package com.jwk.tgdice.biz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwk.tgdice.biz.entity.DicePrizeResult;
import com.jwk.tgdice.dto.DiceBetDto;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jiwk
 * @since 2023-03-19
 */
public interface DicePrizeResultMapper extends BaseMapper<DicePrizeResult> {

  /**
   * 批量插入 仅适用于mysql
   *
   * @param entityList 实体列表
   * @return 影响行数
   */
  Integer insertBatchSomeColumn(Collection<DicePrizeResult> entityList);

  List<DiceBetDto> getPrizeBet(Long timeId, Long userId);


}
