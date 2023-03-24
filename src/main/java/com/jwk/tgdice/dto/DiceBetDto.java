package com.jwk.tgdice.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 投注信息
 * @date 2023/3/19
 */
@Data
public class DiceBetDto {

    /**
     * id
     */
    private Long id;

    /**
     * 期数id
     */
    private Long timeId;

    /**
     * 日期
     */
    private String diceDate;

    /**
     * 期数
     */
    private String timeNo;

    /**
     * 投注金额
     */
    private Integer betAmount;

    /**
     * 金额类型
     */
    private Integer amountTypeId;

    /**
     * 投注类型
     */
    private String betType;

    /**
     * 玩法类型
     */
    private Integer playTypeId;

    /**
     * 中奖类型Code
     */
    private Integer prizeId;

    /**
     * 掷骰子用户id
     */
    private Long betUserId;

    /**
     * 掷骰子用户Name
     */
    private String diceUserName;


    /**
     * 是否中奖
     */
    private Byte isPrize;

    /**
     * 倍率
     */
    private String prizeRate;

    /**
     * createTime
     */
    private LocalDateTime createTime;

    /**
     * updateTime
     */
    private LocalDateTime updateTime;
}
