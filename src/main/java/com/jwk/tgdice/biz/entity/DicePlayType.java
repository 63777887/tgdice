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
public class DicePlayType extends Model<DicePlayType> {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 玩法类型
   */
  private String playType;

  /**
   * 中奖规则
   */
  private String playRule;

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
