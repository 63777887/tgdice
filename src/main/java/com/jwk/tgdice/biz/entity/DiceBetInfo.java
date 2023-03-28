package com.jwk.tgdice.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author jiwk
 * @since 2023-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DiceBetInfo extends Model<DiceBetInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 期数id
     */
    private Long timeId;

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
     * createTime
     */
    private Date createTime;

    /**
     * updateTime
     */
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
