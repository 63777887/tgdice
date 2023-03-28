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
 *
 * </p>
 *
 * @author jiwk
 * @since 2023-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DicePrize extends Model<DicePrize> {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 中奖类型Code
   */
  private String prizeCode;

  /**
   * 中奖类型
   */
  private String prizeValue;

  /**
   * 倍率
   */
  private BigDecimal prizeRate;

  /**
   * updateTime
   */
  private Date updateTime;

  /**
   * createTime
   */
  private Date createTime;


  @Override
  protected Serializable pkVal() {
    return this.id;
  }

}
