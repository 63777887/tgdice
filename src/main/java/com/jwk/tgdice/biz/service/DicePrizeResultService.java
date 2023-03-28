package com.jwk.tgdice.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwk.tgdice.biz.entity.DicePrizeResult;
import com.jwk.tgdice.dto.DiceBetDto;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jiwk
 * @since 2023-03-19
 */
public interface DicePrizeResultService extends IService<DicePrizeResult> {

  List<DiceBetDto> getPrizeBet(Long timeId, Long userId);
}
