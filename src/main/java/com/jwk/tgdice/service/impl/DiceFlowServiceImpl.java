package com.jwk.tgdice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.jwk.tgdice.biz.dao.DicePrizeResultMapper;
import com.jwk.tgdice.biz.entity.Dice;
import com.jwk.tgdice.biz.entity.DicePrize;
import com.jwk.tgdice.biz.entity.DicePrizeResult;
import com.jwk.tgdice.biz.entity.DiceResult;
import com.jwk.tgdice.biz.service.*;
import com.jwk.tgdice.content.DiceCaCheContent;
import com.jwk.tgdice.dto.DiceBetDto;
import com.jwk.tgdice.enums.DicePrizeEnumsE;
import com.jwk.tgdice.enums.StatusE;
import com.jwk.tgdice.service.DiceFlowService;
import com.jwk.tgdice.util.DiceBetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 掷骰子流程操作
 * @date 2023/3/19
 */
@Service
public class DiceFlowServiceImpl implements DiceFlowService {
    @Autowired
    DiceService diceService;
    @Autowired
    DiceResultService diceResultService;
    @Autowired
    DicePrizeService dicePrizeService;
    @Autowired
    DicePrizeResultService dicePrizeResultService;

    @Autowired
    TelegramLongPollingBot telegramLongPollingBot;
    @Autowired
    DiceBetInfoService diceBetInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateDicePrizeResult(Dice dice) {
        List<DiceResult> diceResultList = diceResultService.lambdaQuery().eq(DiceResult::getTimeId, dice.getId()).list();

        List<Integer> resultList = new ArrayList<>();
        for (DiceResult diceResult : diceResultList) {
            resultList.add(diceResult.getDiceResult());
        }
        resultList = resultList.stream().sorted().collect(Collectors.toList());
        List<String> prizeCodes = new ArrayList<>();
        List<String> prizeNames = new ArrayList<>();
        List<DicePrizeResult> dicePrizes = new ArrayList<>();
        List<DicePrize> dicePrizeList = dicePrizeService.list();
        if (resultList.size() >= 3) {

            if (resultList.get(0).equals(resultList.get(2))) {
                System.out.println("豹子");
                prizeCodes.add(DicePrizeEnumsE.BaoZi.getCode());
                prizeNames.add(DicePrizeEnumsE.BaoZi.getValue());
            } else if (resultList.get(0) + 1 == resultList.get(1) && resultList.get(1) + 1 == resultList.get(2)) {
                prizeCodes.add(DicePrizeEnumsE.ShunZi.getCode());
                prizeNames.add(DicePrizeEnumsE.ShunZi.getValue());
            } else if (resultList.get(0).equals(resultList.get(1)) || resultList.get(1).equals(resultList.get(2))) {
                prizeCodes.add(DicePrizeEnumsE.DuiZi.getCode());
                prizeNames.add(DicePrizeEnumsE.DuiZi.getValue());
            } else {
                prizeCodes.add(DicePrizeEnumsE.Za.getCode());
                prizeNames.add(DicePrizeEnumsE.Za.getValue());
            }
            if (diceResultList.get(0).getDiceResult() > diceResultList.get(2).getDiceResult()) {
                prizeCodes.add(DicePrizeEnumsE.Long.getCode());
                prizeNames.add(DicePrizeEnumsE.Long.getValue());
            } else if (diceResultList.get(0).getDiceResult() < diceResultList.get(2).getDiceResult()) {
                prizeCodes.add(DicePrizeEnumsE.Hu.getCode());
                prizeNames.add(DicePrizeEnumsE.Hu.getValue());
            } else if (diceResultList.get(0).getDiceResult().equals(diceResultList.get(2).getDiceResult())) {
                prizeCodes.add(DicePrizeEnumsE.He.getCode());
                prizeNames.add(DicePrizeEnumsE.He.getValue());
            }
            int sum = resultList.get(0) + resultList.get(1) + resultList.get(2);
            // 此时三个骰子不是豹子也不是顺子，可以继续按照之前的方法输出“大”、“小”、“单”或“双”。
            if (sum >= 10) {
                System.out.print("大");
                prizeCodes.add(DicePrizeEnumsE.Da.getCode());
                prizeNames.add(DicePrizeEnumsE.Da.getValue());
            } else {
                prizeCodes.add(DicePrizeEnumsE.Xiao.getCode());
                prizeNames.add(DicePrizeEnumsE.Xiao.getValue());
            }

            if (sum % 2 == 0) {
                prizeCodes.add(DicePrizeEnumsE.Shuang.getCode());
                prizeNames.add(DicePrizeEnumsE.Shuang.getValue());
            } else {
                prizeCodes.add(DicePrizeEnumsE.Dan.getCode());
                prizeNames.add(DicePrizeEnumsE.Dan.getValue());
            }
            if (prizeCodes.contains(DicePrizeEnumsE.Da.getCode()) && prizeCodes.contains(DicePrizeEnumsE.Dan.getCode())) {
                prizeCodes.add(DicePrizeEnumsE.DaDan.getCode());
                prizeNames.add(DicePrizeEnumsE.DaDan.getValue());
            }
            if (prizeCodes.contains(DicePrizeEnumsE.Da.getCode()) && prizeCodes.contains(DicePrizeEnumsE.Shuang.getCode())) {
                prizeCodes.add(DicePrizeEnumsE.DaShuang.getCode());
                prizeNames.add(DicePrizeEnumsE.DaShuang.getValue());
            }
            if (prizeCodes.contains(DicePrizeEnumsE.Xiao.getCode()) && prizeCodes.contains(DicePrizeEnumsE.Dan.getCode())) {
                prizeCodes.add(DicePrizeEnumsE.XiaoDan.getCode());
                prizeNames.add(DicePrizeEnumsE.XiaoDan.getValue());
            }
            if (prizeCodes.contains(DicePrizeEnumsE.Xiao.getCode()) && prizeCodes.contains(DicePrizeEnumsE.Shuang.getCode())) {
                prizeCodes.add(DicePrizeEnumsE.XiaoShuang.getCode());
                prizeNames.add(DicePrizeEnumsE.XiaoShuang.getValue());
            }
            for (String code : prizeCodes) {
                for (DicePrize dicePrize : dicePrizeList) {
                    if (code.equals(dicePrize.getPrizeCode())) {
                        DicePrizeResult dicePrizeResult = new DicePrizeResult();
                        dicePrizeResult.setTimeId(dice.getId());
                        dicePrizeResult.setPrizeId(dicePrize.getId());
                        dicePrizeResult.setCreateTime(LocalDateTime.now());
                        dicePrizes.add(dicePrizeResult);
                    }
                }
            }
            ((DicePrizeResultMapper) dicePrizeResultService.getBaseMapper()).insertBatchSomeColumn(dicePrizes);
            String result = diceResultList.get(0).getDiceResult() + "," + diceResultList.get(1).getDiceResult() + "," + diceResultList.get(2).getDiceResult() + " " + String.join("|", prizeNames);
            diceService.lambdaUpdate().set(Dice::getStatus, StatusE.Delete.getId()).set(Dice::getResult, result).eq(Dice::getId, dice.getId()).update();
            return result;
        }
        return "";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void start() throws IOException, TelegramApiException {
        if (CollUtil.isNotEmpty(DiceCaCheContent.groupCache)) {
            for (Long groupId : DiceCaCheContent.groupCache) {
                diceService.lambdaUpdate().set(Dice::getStatus, StatusE.Delete.getId()).eq(Dice::getGroupId, groupId).update();
                DateTime closeTime = DiceBetUtil.getClosingTime();
                DateTime lotteryTime = DiceBetUtil.getLotteryTime();
                Dice newDice = new Dice();
                newDice.setGroupId(groupId);
                newDice.setDiceDate(DiceBetUtil.getDiceDate());
                newDice.setStatus(StatusE.Normal.getId());
                newDice.setTimeNo(DiceBetUtil.getTimeNo());
                newDice.setCreateTime(LocalDateTime.now());
                newDice.setClosingTime(LocalDateTimeUtil.of(closeTime));
                newDice.setLotteryTime(LocalDateTimeUtil.of(lotteryTime));
                diceService.save(newDice);
                String sendText = newDice.getDiceDate() + String.format("%03d", newDice.getTimeNo()) + "期开始下注\n【开始下注】\n封盘时间: " + closeTime + "\n开奖时间: " + lotteryTime;
                ClassPathResource classPathResource = new ClassPathResource("static/img/xiazhu.jpg");
                telegramLongPollingBot.execute(SendPhoto.builder().caption(sendText).photo(new InputFile(classPathResource.getInputStream(), classPathResource.getFilename())).chatId(String.valueOf(groupId)).build());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sealing() throws TelegramApiException, IOException {
        if (CollUtil.isNotEmpty(DiceCaCheContent.groupCache)) {
            for (Long groupId : DiceCaCheContent.groupCache) {
                Dice dice = diceService.lambdaQuery().eq(Dice::getGroupId, groupId).eq(Dice::getStatus, StatusE.Normal.getId()).one();
                if (BeanUtil.isEmpty(dice)) {
                    return;
                }
                List<DiceBetDto> diceBetInfoList = diceBetInfoService.getBetInfoListByTimeNo(DiceBetUtil.getDiceDate(), DiceBetUtil.getTimeNo());
                StringBuilder sendText = new StringBuilder("⛔️已封盘，停止下注！\n本期下注玩家:\n");
                if (CollUtil.isNotEmpty(diceBetInfoList)) {
                    for (DiceBetDto diceBetDto : diceBetInfoList) {
                        sendText.append(DiceBetUtil.getBetInfo(diceBetDto));
                    }
                }
                Map<Long, List<DiceBetDto>> map = diceBetInfoList.stream().collect(Collectors.groupingBy(DiceBetDto::getBetUserId));
                Integer maxTotalBet = 0;
                String userName = "无用户";
                Long userId = 0L;
                if (CollUtil.isNotEmpty(map.values())) {
                    for (List<DiceBetDto> diceBetDtos : map.values()) {
                        Integer totalAmount = 0;
                        for (DiceBetDto diceBetDto : diceBetDtos) {
                            totalAmount += diceBetDto.getBetAmount();
                        }
                        if (totalAmount > maxTotalBet) {
                            maxTotalBet = totalAmount;
                            userName = diceBetDtos.get(0).getDiceUserName();
                            userId = diceBetDtos.get(0).getBetUserId();
                        }
                    }
                }
                //请掷骰子玩家: 北梦有事直说 (总投注:325)

                diceService.lambdaUpdate().set(Dice::getRollUserId, userId).set(Dice::getRollUserName, userName).set(Dice::getStatus, StatusE.FengPan.getId()).eq(Dice::getId, dice.getId()).update();
                sendText.append("\n请掷骰子玩家: " + userName + "(总投注:" + maxTotalBet + ")\n\n");
                sendText.append("\uD83D\uDC49轻触【" + "<code>  \uD83C\uDFB2   </code>" + "】复制投掷。\n" + "⚠️封盘后15秒内掷出三颗骰子，超时由机器人补发，以机器人录入为准，无争议！");
                ClassPathResource classPathResource = new ClassPathResource("static/img/tztz.jpg");
                SendPhoto sendPhoto = SendPhoto.builder().parseMode("HTML").caption(sendText.toString()).photo(new InputFile(classPathResource.getInputStream(), classPathResource.getFilename())).chatId(String.valueOf(groupId)).build();
                telegramLongPollingBot.execute(sendPhoto);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void end() throws TelegramApiException, IOException {
        if (CollUtil.isNotEmpty(DiceCaCheContent.groupCache)) {
            for (Long groupId : DiceCaCheContent.groupCache) {
                String result = "";
                Dice dice = diceService.lambdaQuery().eq(Dice::getGroupId, groupId).orderBy(true, false, Dice::getId).last("limit 1").one();
                if (BeanUtil.isEmpty(dice)) {
                    return;
                }
                Integer count = diceResultService.lambdaQuery().eq(DiceResult::getTimeId, dice.getId()).count();
                if (count < 3) {
                    for (int i = 0; i < 3 - count; i++) {
                        SendDice sendDice = new SendDice();
                        sendDice.setChatId(groupId.toString());
                        Message execute = telegramLongPollingBot.execute(sendDice);
                        DiceResult diceResult = new DiceResult();
                        diceResult.setDiceCount(i + 1 + count);
                        diceResult.setTimeId(dice.getId());
                        diceResult.setDiceUserId(groupId);
                        diceResult.setDiceResult(execute.getDice().getValue());
                        diceResult.setCreateTime(LocalDateTime.now());
                        diceResultService.save(diceResult);
                        if (i + count == 2) {
                            result = updateDicePrizeResult(dice);
                        }
                    }
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(groupId.toString());
                    sendMessage.setText("玩家未投掷骰子，已由机器人投掷");
                    telegramLongPollingBot.execute(sendMessage);
                }

                List<DiceBetDto> diceBetDtoList = dicePrizeResultService.getPrizeBet(dice.getId(), null);

                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(groupId));
                //20230318683期开奖结果
                //4+1+4=9 小单🈴️
                //--------本期中奖玩家--------
                //二老板-6115272852单 600(2倍率)
                //二老板-6115272852小单 440(4.4倍率)
                //二老板-6115272852 和值9 300(6倍率)
                StringBuilder sendText = new StringBuilder(dice.getDiceDate() + String.format("%03d", dice.getTimeNo()) + "期开奖结果" + "\n" + result + "\n");
                sendText.append("--------本期中奖玩家--------\n");
                for (DiceBetDto diceBetDto : diceBetDtoList) {
                    sendText.append(DiceBetUtil.getBetInfo(diceBetDto));
                }
                message.setText(sendText.toString());
                ClassPathResource classPathResource = new ClassPathResource("static/img/gxzj.jpg");
                SendPhoto sendPhoto = SendPhoto.builder().caption(sendText.toString()).photo(new InputFile(classPathResource.getInputStream(), classPathResource.getFilename())).chatId(String.valueOf(groupId)).build();
                telegramLongPollingBot.execute(sendPhoto);
            }
        }
    }

    @Override
    public void remind() throws TelegramApiException, IOException {
        if (CollUtil.isNotEmpty(DiceCaCheContent.groupCache)) {
            for (Long groupId : DiceCaCheContent.groupCache) {
                //封盘剩余10秒 即将封盘
                //请投注最高玩家准备掷骰子
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(groupId.toString());
                sendMessage.setText("封盘剩余10秒 即将封盘\n" +
                        "请投注最高玩家准备掷骰子");
                telegramLongPollingBot.execute(sendMessage);
            }
        }
    }

}


