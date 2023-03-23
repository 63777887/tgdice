package com.jwk.tgdice.service.impl.action;

import cn.hutool.core.bean.BeanUtil;
import com.jwk.tgdice.biz.entity.DiceAccount;
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
 * 余额按钮规则
 * @date 2023/3/18
 */
@Service
@Slf4j
public class ButtonBalanceService implements ActionService {

    @Autowired
    DiceAccountService diceAccountService;

    @Autowired
    TelegramLongPollingBot telegramLongPollingBot;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Update update) throws BetMessageException, TelegramApiException {
        DiceAccount diceAccount = diceAccountService.lambdaQuery().eq(DiceAccount::getUserId, update.getCallbackQuery().getFrom().getId()).one();
        // 弹出一个列表框
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(true);
        StringBuilder sendText = new StringBuilder();
        sendText.append("用户：").append(update.getCallbackQuery().getFrom().getFirstName()).append(update.getCallbackQuery().getFrom().getLastName()).append("\n余额：");
        if (BeanUtil.isNotEmpty(diceAccount)) {
            sendText.append(diceAccount.getBalance()).append("\n流水：").append(diceAccount.getFlow());
            // 发送消息
        } else {
            sendText.append("0").append("\n流水：").append("0");
            // 发送消息
        }
        answerCallbackQuery.setText(sendText.toString());
        telegramLongPollingBot.execute(answerCallbackQuery);
    }

    @Override
    public boolean support(Update update) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("balance");
    }
}
