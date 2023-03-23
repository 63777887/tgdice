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
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
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

    @Autowired
    DiceAccountService diceAccountService;

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
//        // 处理回调数据
//        if (update.hasCallbackQuery()) {
//            // 获取回调数据
//            String callbackData = update.getCallbackQuery().getData();
//            if (callbackData.equals("remainder")) {
//                DiceAccount diceAccount = diceAccountService.lambdaQuery().eq(DiceAccount::getUserId, update.getCallbackQuery().getFrom().getId()).one();
//                // 弹出一个列表框
//                AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
//                answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
//                answerCallbackQuery.setShowAlert(true);
//                StringBuilder sendText = new StringBuilder();
//                sendText.append("用户：").append(update.getCallbackQuery().getFrom().getFirstName()).append(update.getCallbackQuery().getFrom().getLastName()).append("\n余额：");
//                if (BeanUtil.isNotEmpty(diceAccount)) {
//                    sendText.append(diceAccount.getBalance()).append("\n流水：").append(diceAccount.getFlow());
//                    // 发送消息
//                } else {
//                    sendText.append("0").append("\n流水：").append("0");
//                    // 发送消息
//                }
//                answerCallbackQuery.setText(sendText.toString());
//                super.execute(answerCallbackQuery);
//            }
//            if (callbackData.equals("flow")) {
//                // 弹出一个列表框
//                AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
//                answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
//                answerCallbackQuery.setShowAlert(true);
//                answerCallbackQuery.setText("期数\t\t\t\t\t\t投注\t\t\t\t\t\t金额\t\t\t\t\t\t赔付\n");
//                // 发送消息
//                super.execute(answerCallbackQuery);
//            }
//
//        }
//        if (update.hasMessage() && update.getMessage().getText().equals("test")) {
//            // 创建键盘
//            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//            List<InlineKeyboardButton> rowInline = new ArrayList<>();
//            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//            inlineKeyboardButton.setText("余额");
//            inlineKeyboardButton.setCallbackData("remainder");
//            rowInline.add(inlineKeyboardButton);
//
//            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
//            inlineKeyboardButton1.setText("最近下注");
//            inlineKeyboardButton1.setCallbackData("recentlyBet");
//            rowInline.add(inlineKeyboardButton1);
//            rowsInline.add(rowInline);
//            markupInline.setKeyboard(rowsInline);
//            // 创建发送消息
//            SendMessage message = new SendMessage();
//            message.setChatId(update.getMessage().getChatId().toString());
//            message.setText("Message text");
//            message.setReplyMarkup(markupInline);
//            super.execute(message);
//        }
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
            Message msg = update.getMessage();
            Long chatId = msg.getChatId();
            String content = msg.getText();
            List<BetEntity> sendMessages = new ArrayList<>();
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
                Dice dice = diceService.lambdaQuery().eq(Dice::getGroupId, chatId).eq(Dice::getStatus, StatusE.Normal.getId()).one();
                if (BeanUtil.isEmpty(dice)) {
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
