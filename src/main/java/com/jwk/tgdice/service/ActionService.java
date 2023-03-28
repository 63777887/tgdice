package com.jwk.tgdice.service;

import com.jwk.tgdice.exception.BetMessageException;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 动作规则
 * @date 2023/3/16
 */
public interface ActionService {

    void onMessage(Update update) throws BetMessageException, TelegramApiException;

    default int order() {
        return 0;
    }

    boolean support(Update update);
}
