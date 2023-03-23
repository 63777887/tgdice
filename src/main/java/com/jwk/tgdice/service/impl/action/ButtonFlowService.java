package com.jwk.tgdice.service.impl.action;

import com.jwk.tgdice.biz.service.DiceAccountService;
import com.jwk.tgdice.exception.BetMessageException;
import com.jwk.tgdice.service.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 流水按钮规则
 * @date 2023/3/18
 */
@Service
@Slf4j
public class ButtonFlowService implements ActionService {

    @Autowired
    DiceAccountService diceAccountService;

    @Autowired
    TelegramLongPollingBot telegramLongPollingBot;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Update update) throws BetMessageException, TelegramApiException {
        // 弹出一个列表框
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText("期数\t\t\t\t\t\t投注\t\t\t\t\t\t金额\t\t\t\t\t\t赔付\n");
        // 发送消息
        telegramLongPollingBot.execute(answerCallbackQuery);
    }

    @Override
    public boolean support(Update update) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("flow");
    }
}
