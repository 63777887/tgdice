package com.jwk.tgdice.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Dice extends Model<Dice> {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 当前日期
   */
  private String diceDate;

  /**
   * 期数
   */
  private Integer timeNo;

  /**
   * 结果
   */
  private String result;

  /**
   * 开奖时间
   */
  private Date lotteryTime;

  /**
   * 封盘时间
   */
  private Date closingTime;

  /**
   * 掷骰子用户Id
   */
  private Long rollUserId;

  /**
   * 掷骰子用户Name
   */
  private String rollUserName;

  /**
   * 群Id
   */
  private Long groupId;

  /**
   * 状态
   */
  private Byte status;


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
