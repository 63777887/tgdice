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
public class DiceResult extends Model<DiceResult> {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 期数id
   */
  private Long timeId;

  /**
   * 骰子结果
   */
  private Integer diceResult;

  /**
   * 骰子次数
   */
  private Integer diceCount;

  /**
   * 掷骰子用户id
   */
  private Long diceUserId;

  /**
   * 掷骰子用户Name
   */
  private String diceUserName;

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
