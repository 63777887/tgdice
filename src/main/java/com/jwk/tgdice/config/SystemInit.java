package com.jwk.tgdice.config;

import cn.hutool.core.collection.CollUtil;
import com.jwk.tgdice.biz.entity.DicePlayType;
import com.jwk.tgdice.biz.service.DicePlayTypeService;
import com.jwk.tgdice.content.DiceCaCheContent;
import com.jwk.tgdice.service.MyTelegramBot;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Component
@Slf4j
public class SystemInit implements ApplicationRunner {

    @Autowired
    DicePlayTypeService dicePlayTypeService;
    @Autowired
    private MyTelegramBot myTelegramBot;

    @Override
    @SneakyThrows
    public void run(ApplicationArguments args) throws Exception {
        List<DicePlayType> playTypeList = dicePlayTypeService.list();
        if (CollUtil.isNotEmpty(playTypeList)) {
            for (DicePlayType dicePlayType : playTypeList) {
                DiceCaCheContent.dicePlayTypeTypeCache.put(dicePlayType.getPlayType(), dicePlayType);
            }
        }
        // Get the updates from Telegram
        GetUpdates getUpdates = new GetUpdates();
        try {
            List<Update> updates = myTelegramBot.execute(getUpdates);
            // Loop through the updates and extract the chat IDs of the groups
            for (Update update : updates) {
                if (update.hasMessage() && update.getMessage().getChat().isGroupChat()) {
                    Long chatId = update.getMessage().getChat().getId();
                    if (!DiceCaCheContent.groupCache.contains(chatId)) {
                        DiceCaCheContent.groupCache.add(chatId);
                    }
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(myTelegramBot);
        log.info("机器人已启动！");
    }
}