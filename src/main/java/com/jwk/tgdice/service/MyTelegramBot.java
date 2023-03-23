package com.jwk.tgdice.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.jwk.tgdice.biz.entity.*;
import com.jwk.tgdice.biz.entity.Dice;
import com.jwk.tgdice.biz.dao.DiceBetInfoMapper;
import com.jwk.tgdice.biz.service.*;
import com.jwk.tgdice.config.TgProperties;
import com.jwk.tgdice.content.DiceCaCheContent;
import com.jwk.tgdice.entity.BetEntity;
import com.jwk.tgdice.enums.DicePrizeEnumsE;
import com.jwk.tgdice.enums.StatusE;
import com.jwk.tgdice.exception.BetMessageException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MyTelegramBot extends TelegramLongPollingBot {

    @Autowired
    TgProperties tgProperties;

    @Autowired
    DiceBetInfoService diceBetInfoService;

    @Autowired
    DiceService diceService;

    @Autowired
    DiceResultService diceResultService;

    @Autowired
    DicePrizeService dicePrizeService;

    @Autowired
    DicePrizeResultService dicePrizeResultService;

    @Override
    public String getBotUsername() {
        return tgProperties.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return tgProperties.getBotToken();
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getChat().isGroupChat()) {
            Long chatId = update.getMessage().getChat().getId();
            if (!DiceCaCheContent.groupCache.contains(chatId)) {
                DiceCaCheContent.groupCache.add(chatId);
            }
        }

        if (update.hasMessage() && CollUtil.isNotEmpty(update.getMessage().getNewChatMembers())) {
            String chatId = update.getMessage().getChatId().toString();
            String newMembers = update.getMessage().getNewChatMembers().stream()
                    .map(User::getUserName)
                    .collect(Collectors.joining(", "));
            String messageText = "欢迎 " + newMembers + " 加入本群！";
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(messageText);
            sendMessage(message);
        }
//
//        if (update.hasMessage() && update.getMessage().isGroupMessage()) {
//            // 处理群聊消息
//        } else if (update.hasChatMember()) {
//            // 处理群聊成员更新事件
//            ChatMemberUpdated chatMemberUpdated = update.getChatMemberUpdated();
//            if (chatMemberUpdated.getNewChatMember().getUser().getId().equals(getBotId())) {
//                // 新成员是机器人本身，不做处理
//                return;
//            }
//            if (chatMemberUpdated.getNewChatMember().getStatus().equals(ChatMemberStatus.MEMBER)) {
//                // 新成员加入了群聊，需要进行问题验证
//                askQuestion(update.getMessage().getChatId(), chatMemberUpdated.getNewChatMember().getUser().getId());
//            }
//        }

        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String content = msg.getText();
        List<BetEntity> sendMessages = new ArrayList<>();

        Map<String, ActionService> actionServiceMap = SpringUtil.getBeansOfType(ActionService.class);
        actionServiceMap.values().stream().sorted(Comparator.comparing(ActionService::order).reversed())
                .forEach(actionService -> {
                            if (actionService.support(update)) {
                                try {
                                    actionService.onMessage(update);
                                } catch (TelegramApiException | BetMessageException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                );

        if (update.hasMessage() && update.getMessage().hasText()) {
            Map<String, RuleService> ruleServiceMap = SpringUtil.getBeansOfType(RuleService.class);
            AtomicBoolean needSend = new AtomicBoolean(true);
            StringBuilder stringBuilder = new StringBuilder(content);
            ruleServiceMap.values().stream().sorted(Comparator.comparing(RuleService::order).reversed())
                    .forEach(ruleService -> {
                        if (ruleService.support(stringBuilder.toString())) {
                            if (needSend.get()) {
                                List<BetEntity> betEntities = null;
                                try {
                                    betEntities = ruleService.onMessage(stringBuilder, update);
                                    if (CollUtil.isNotEmpty(betEntities)) {
                                        sendMessages.addAll(betEntities);
                                    }
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                } catch (BetMessageException e) {
                                    needSend.set(false);
                                }
                            }
                        }
                    });

            if (needSend.get() && CollUtil.isNotEmpty(sendMessages) && StrUtil.isBlank(stringBuilder.toString())) {
                List<DicePrize> dicePrizeList = dicePrizeService.list();
                Dice dice = diceService.lambdaQuery().eq(Dice::getGroupId,chatId).eq(Dice::getStatus, StatusE.Normal.getId()).one();
                if (BeanUtil.isEmpty(dice)){
                    return;
                }
                SendMessage message = new SendMessage();
                message.setChatId(chatId.toString());
                StringBuilder messageText = new StringBuilder();
                List<DiceBetInfo> diceBetInfos = new ArrayList<>();
                for (BetEntity sendMessage : sendMessages) {

                    DicePlayType dicePlayType = DiceCaCheContent.dicePlayTypeTypeCache.get(sendMessage.getPlayType());
                    if (BeanUtil.isEmpty(dicePlayType)) {
                        throw new BetMessageException("非法的玩法");
                    }
                    DiceBetInfo diceBetInfo = new DiceBetInfo();
                    diceBetInfo.setBetAmount(sendMessage.getValue());
                    diceBetInfo.setBetType(sendMessage.getBetType());
                    diceBetInfo.setPlayTypeId(dicePlayType.getId());
                    diceBetInfo.setAmountTypeId(1);
                    diceBetInfo.setTimeId(dice.getId());
                    for (DicePrize dicePrize : dicePrizeList) {
                        if (dicePrize.getPrizeCode().equals(sendMessage.getBetType())) {
                            diceBetInfo.setPrizeId(dicePrize.getId());
                        }
                    }
                    diceBetInfo.setBetUserId(update.getMessage().getFrom().getId());
                    diceBetInfo.setCreateTime(LocalDateTime.now());
                    diceBetInfo.setDiceUserName(update.getMessage().getFrom().getFirstName() + update.getMessage().getFrom().getLastName());
                    diceBetInfos.add(diceBetInfo);
                    messageText.append(sendMessage.getPlayType()).append(": ").append(DicePrizeEnumsE.getValueByCode(sendMessage.getBetType())).append(" ").append(sendMessage.getValue()).append(" 特殊值：").append(sendMessage.getSpecial() + "\n");
                }
                DiceBetInfoMapper baseMapper = (DiceBetInfoMapper) diceBetInfoService.getBaseMapper();
                baseMapper.insertBatchSomeColumn(diceBetInfos);
                message.setText(messageText.toString());
                message.setReplyToMessageId(update.getMessage().getMessageId());
                sendMessage(message);
            }
        }
    }



    //发送消息
    private void sendMessage(SendMessage message) {
        try {
            super.execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
