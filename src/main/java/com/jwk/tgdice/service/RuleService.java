package com.jwk.tgdice.service;

import com.jwk.tgdice.entity.BetEntity;
import com.jwk.tgdice.exception.BetMessageException;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 动作规则
 * @date 2023/3/16
 */
public interface RuleService {

    List<BetEntity> onMessage(StringBuilder message, Update update) throws BetMessageException, TelegramApiException;

    boolean support(String message);

    default int order() {
        return 0;
    }
}
