package com.jwk.tgdice.biz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwk.tgdice.biz.entity.DicePlayType;
import java.util.Collection;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jiwk
 * @since 2023-03-18
 */
public interface DicePlayTypeMapper extends BaseMapper<DicePlayType> {

  /**
   * 批量插入 仅适用于mysql
   *
   * @param entityList 实体列表
   * @return 影响行数
   */
  Integer insertBatchSomeColumn(Collection<DicePlayType> entityList);
}
