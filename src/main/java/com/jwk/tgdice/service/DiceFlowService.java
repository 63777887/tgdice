package com.jwk.tgdice.service;

import com.jwk.tgdice.biz.entity.Dice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 掷骰子流程操作
 * @date 2023/3/19
 */
public interface DiceFlowService {
    String updateDicePrizeResult(Dice dice);

    void start() throws IOException, TelegramApiException;

    void sealing() throws TelegramApiException, IOException;

    void end() throws TelegramApiException, IOException;

    void remind() throws TelegramApiException, IOException;
}
