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
public class DicePrizeResult extends Model<DicePrizeResult> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 期数id
     */
    private Long timeId;

    /**
     * 中奖类型Code
     */
    private Integer prizeId;

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
