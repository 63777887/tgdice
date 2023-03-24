package com.jwk.tgdice.biz.service;

import com.jwk.tgdice.biz.entity.DiceBetInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jwk.tgdice.dto.DiceBetDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiwk
 * @since 2023-03-19
 */
public interface DiceBetInfoService extends IService<DiceBetInfo> {
    List<DiceBetDto> getBetInfoListByTimeNo(Long diceId);

    List<DiceBetDto> getAllBetInfo(Long timeId, Long userId);
}
