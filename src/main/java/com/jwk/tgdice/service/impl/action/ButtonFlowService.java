package com.jwk.tgdice.service.impl.action;

import cn.hutool.core.collection.CollUtil;
import com.jwk.tgdice.biz.service.DiceAccountService;
import com.jwk.tgdice.biz.service.DiceBetInfoService;
import com.jwk.tgdice.dto.DiceBetDto;
import com.jwk.tgdice.enums.DicePrizeEnumsE;
import com.jwk.tgdice.enums.IsPrizeEnumsE;
import com.jwk.tgdice.exception.BetMessageException;
import com.jwk.tgdice.service.ActionService;
import com.jwk.tgdice.util.DiceBetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    DiceBetInfoService diceBetInfoService;

    @Autowired
    TelegramLongPollingBot telegramLongPollingBot;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Update update) throws BetMessageException, TelegramApiException {
        // 弹出一个列表框
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(true);
        StringBuilder sendText = new StringBuilder("期数\t\t\t\t\t\t投注\t\t\t\t\t\t金额\t\t\t\t\t\t赔付\n");
        List<DiceBetDto> diceBetDtoList = diceBetInfoService.getAllBetInfo(null, update.getCallbackQuery().getFrom().getId());
        if (CollUtil.isNotEmpty(diceBetDtoList)) {
            Map<Long, List<DiceBetDto>> map = diceBetDtoList.stream().collect(Collectors.groupingBy(DiceBetDto::getTimeId));
            map.forEach((k, v) -> {
                if (v.stream().anyMatch(t -> t.getIsPrize().equals(IsPrizeEnumsE.IsPrize.getId()) && t.getBetType().equals(DicePrizeEnumsE.Meng.getCode()))) {
                    v = v.stream().filter(t -> t.getBetType().equals(DicePrizeEnumsE.Meng.getCode())).collect(Collectors.toList());
                    map.put(k, v);
                }
                if (v.stream().anyMatch(t -> t.getIsPrize().equals(IsPrizeEnumsE.IsPrize.getId()) && t.getBetType().equals(DicePrizeEnumsE.BaoZi.getCode()))) {
                    v = v.stream().filter(t -> t.getBetType().equals(DicePrizeEnumsE.BaoZi.getCode())).collect(Collectors.toList());
                    map.put(k, v);
                }
            });
            map.forEach((k, v) -> {
                if (CollUtil.isNotEmpty(v)) {
                    for (DiceBetDto diceBetDto : v) {
                        sendText.append(v.get(0).getDiceDate()).append(v.get(0).getTimeNo()).append("\t").append(DiceBetUtil.getBetFlowInfo(diceBetDto));
                        sendText.append("\n");
                    }

                }
            });
        }
        answerCallbackQuery.setText(sendText.toString());
        // 发送消息
        telegramLongPollingBot.execute(answerCallbackQuery);
    }

    @Override
    public boolean support(Update update) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("flow");
    }
}
