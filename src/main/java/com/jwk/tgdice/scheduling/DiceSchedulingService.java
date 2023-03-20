package com.jwk.tgdice.scheduling;

import com.jwk.tgdice.service.DiceFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 掷骰子任务
 * @date 2023/3/20
 */
@Component
public class DiceSchedulingService {

    @Autowired
    DiceFlowService diceFlowService;

    @Scheduled(cron = "5 0/2 * * * *")
    public void start() throws TelegramApiException, IOException {
        diceFlowService.start();
    }

    @Scheduled(cron = "45 1/2 * * * *")
    public void sealing() throws TelegramApiException, IOException {
        diceFlowService.sealing();
    }

    @Scheduled(cron = "0 0/2 * * * *")
    public void end() throws TelegramApiException, IOException {
        diceFlowService.end();
    }

    @Scheduled(cron = "30 1/2 * * * *")
    public void remind() throws TelegramApiException, IOException {
        diceFlowService.remind();
    }

}
