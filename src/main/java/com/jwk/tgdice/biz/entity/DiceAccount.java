package com.jwk.tgdice.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 账户表
 * </p>
 *
 * @author jiwk
 * @since 2023-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DiceAccount extends Model<DiceAccount> {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 用户Id
   */
  private Long userId;

  /**
   * 用户名
   */
  private String userName;

  /**
   * 流水
   */
  private BigDecimal flow;

  /**
   * 余额
   */
  private BigDecimal balance;

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
