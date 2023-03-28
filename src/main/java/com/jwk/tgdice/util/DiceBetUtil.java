package com.jwk.tgdice.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.jwk.tgdice.biz.entity.DicePlayType;
import com.jwk.tgdice.content.DiceCaCheContent;
import com.jwk.tgdice.dto.DiceBetDto;
import com.jwk.tgdice.enums.DicePrizeEnumsE;
import com.jwk.tgdice.enums.IsPrizeEnumsE;
import com.jwk.tgdice.exception.BetMessageException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 押注工具类
 * @date 2023/3/18
 */
public class DiceBetUtil {

  public static void checkPlayType(String playType) throws BetMessageException {
    DicePlayType dicePlayType = DiceCaCheContent.dicePlayTypeTypeCache.get(playType);
    if (BeanUtil.isEmpty(dicePlayType)) {
      throw new BetMessageException("非法的玩法");
    }
  }

  public static boolean strEquals(String str1, String str2) throws BetMessageException {
    // 将字符串转换为字符数组，然后排序
    char[] chars1 = str1.toCharArray();
    Arrays.sort(chars1);
    char[] chars2 = str2.toCharArray();
    Arrays.sort(chars2);
    // 将排序后的字符数组转换为字符串，然后比较
    String sortedStr1 = new String(chars1);
    String sortedStr2 = new String(chars2);
    return sortedStr1.equals(sortedStr2);
  }

  public static Integer getTimeNo() {
    // 将字符串转换为字符数组，然后排序

    return Math.toIntExact(
        DateUtil.betweenMs(DateUtil.date(), DateUtil.beginOfDay(DateUtil.date())) / 1000 / 60 / 2)
        + 1;
  }

  public static DateTime getClosingTime() {
    // 将字符串转换为字符数组，然后排序

    return DateUtil.offsetSecond(getLotteryTime(), -20);
  }

  public static DateTime getLotteryTime() {
    // 将字符串转换为字符数组，然后排序

    return DateUtil.offsetMinute(DateUtil.beginOfDay(DateUtil.date()), getTimeNo() * 2);
  }

  public static String getDiceDate() {
    // 将字符串转换为字符数组，然后排序

    return DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
  }

  public static String getBetInfo(DiceBetDto diceBetDto) {
    return diceBetDto.getDiceUserName() + "-" + diceBetDto.getBetUserId() + " " + DicePrizeEnumsE
        .getValueByCode(diceBetDto.getBetType()) + "  " + new BigDecimal(diceBetDto.getBetAmount()).multiply(new BigDecimal(diceBetDto.getPrizeRate())) + "("
        + diceBetDto.getPrizeRate() + "倍率)\n";
  }

  public static String getBetFlowInfo(DiceBetDto diceBetDto) {
    return DicePrizeEnumsE.getValueByCode(diceBetDto.getBetType()) + "\t" + diceBetDto
        .getBetAmount() + "(" + diceBetDto.getPrizeRate() + "倍率)" + "\t" + (
        diceBetDto.getIsPrize().equals(IsPrizeEnumsE.IsPrize.getId()) ? String.valueOf(
            new BigDecimal(diceBetDto.getPrizeRate())
                .multiply(new BigDecimal(diceBetDto.getBetAmount()))) : 0);
  }

  public static void main(String[] args) {
    System.out.println();
  }
}
