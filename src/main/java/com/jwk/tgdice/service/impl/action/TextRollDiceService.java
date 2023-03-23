package com.jwk.tgdice.service.impl.action;

import com.jwk.tgdice.biz.service.DiceResultService;
import com.jwk.tgdice.biz.service.DiceService;
import com.jwk.tgdice.exception.BetMessageException;
import com.jwk.tgdice.service.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 掷骰子规则
 * @date 2023/3/18
 */
@Service
@Slf4j
public class TextRollDiceService implements ActionService {

    @Autowired
    TelegramLongPollingBot telegramLongPollingBot;

    @Autowired
    DiceResultService diceResultService;

    @Autowired
    DiceService diceService;

    @Override
    public void onMessage(Update update) throws BetMessageException, TelegramApiException {
//        String chatId = String.valueOf(update.getMessage().getChatId());
//        SendDice sendDice = new SendDice();
//        sendDice.setChatId(chatId);
//        Message execute = telegramLongPollingBot.execute(sendDice);
//        log.info("本次投掷结果：{}", execute.getDice().getValue());
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//        sendMessage.setText("本次投掷结果：" + execute.getDice().getValue());
//        telegramLongPollingBot.execute(sendMessage);
//        Dice dice = diceService.lambdaQuery().eq(Dice::getGroupId,chatId).eq(Dice::getStatus, StatusE.FengPan.getId()).one();
//        DiceResult lastDiceResult = diceResultService.lambdaQuery().eq(DiceResult::getTimeId, dice.getId()).orderBy(true, false, DiceResult::getDiceCount).last("limit 1").one();
//        if (lastDiceResult == null || lastDiceResult.getDiceCount() < 3) {
//            DiceResult diceResult = new DiceResult();
//            diceResult.setDiceCount(lastDiceResult == null ? 1 : lastDiceResult.getDiceCount() + 1);
//            diceResult.setTimeId(dice.getId());
//            diceResult.setDiceUserId(update.getMessage().getFrom().getId());
//            diceResult.setDiceUserName(update.getMessage().getFrom().getFirstName() + update.getMessage().getFrom().getLastName());
//            diceResult.setDiceResult(execute.getDice().getValue());
//            diceResult.setCreateTime(LocalDateTime.now());
//            diceResultService.save(diceResult);
//        }
    }

    @Override
    public boolean support(Update update) {
        return update.getMessage().hasText() && update.getMessage().getText().equals("掷骰子");
    }
}
