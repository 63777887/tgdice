package com.jwk.tgdice.service.impl.action;

import cn.hutool.core.bean.BeanUtil;
import com.jwk.tgdice.biz.entity.Dice;
import com.jwk.tgdice.biz.entity.DiceResult;
import com.jwk.tgdice.biz.service.DiceResultService;
import com.jwk.tgdice.biz.service.DiceService;
import com.jwk.tgdice.enums.StatusE;
import com.jwk.tgdice.exception.BetMessageException;
import com.jwk.tgdice.service.ActionService;
import com.jwk.tgdice.service.DiceFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 掷骰子规则
 * @date 2023/3/18
 */
@Service
@Slf4j
public class RollDiceService implements ActionService {

    @Autowired
    TelegramLongPollingBot telegramLongPollingBot;

    @Autowired
    DiceResultService diceResultService;

    @Autowired
    DiceService diceService;

    @Autowired
    DiceFlowService diceFlowService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Update update) throws BetMessageException, TelegramApiException {
        String chatId = String.valueOf(update.getMessage().getChatId());
        Dice dice = diceService.lambdaQuery().eq(Dice::getGroupId, chatId).eq(Dice::getStatus, StatusE.FengPan.getId()).one();
        if (dice == null || !dice.getRollUserId().equals(update.getMessage().getFrom().getId())) {
            return;
        }
        log.info("本次投掷结果：{}", update.getMessage().getDice().getValue());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("本次投掷结果：" + update.getMessage().getDice().getValue());
        telegramLongPollingBot.execute(sendMessage);
        if (BeanUtil.isNotEmpty(dice)) {
            DiceResult lastDiceResult = diceResultService.lambdaQuery().eq(DiceResult::getTimeId, dice.getId()).orderBy(true, false, DiceResult::getDiceCount).last("limit 1").one();
            if (lastDiceResult == null || lastDiceResult.getDiceCount() < 3) {
                DiceResult diceResult = new DiceResult();
                diceResult.setDiceCount(lastDiceResult == null ? 1 : lastDiceResult.getDiceCount() + 1);
                diceResult.setTimeId(dice.getId());
                diceResult.setDiceUserId(update.getMessage().getFrom().getId());
                diceResult.setDiceUserName(update.getMessage().getFrom().getFirstName() + update.getMessage().getFrom().getLastName());
                diceResult.setDiceResult(update.getMessage().getDice().getValue());
                diceResult.setCreateTime(LocalDateTime.now());
                diceResultService.save(diceResult);
                if (lastDiceResult != null && lastDiceResult.getDiceCount() == 2) {
                    diceFlowService.updateDicePrizeResult(dice);
                }
            }
        }
    }

    @Override
    public boolean support(Update update) {
        return update.hasMessage() && update.getMessage().hasDice();
    }
}
