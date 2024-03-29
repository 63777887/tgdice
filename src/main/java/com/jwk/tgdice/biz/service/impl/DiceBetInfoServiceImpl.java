package com.jwk.tgdice.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwk.tgdice.biz.dao.DiceBetInfoMapper;
import com.jwk.tgdice.biz.entity.DiceBetInfo;
import com.jwk.tgdice.biz.service.DiceBetInfoService;
import com.jwk.tgdice.dto.DiceBetDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiwk
 * @since 2023-03-19
 */
@Service
public class DiceBetInfoServiceImpl extends ServiceImpl<DiceBetInfoMapper, DiceBetInfo> implements
        DiceBetInfoService {

    @Override
    public List<DiceBetDto> getBetInfoListByTimeId(Long diceId) {
        return getBaseMapper().getBetInfoListByTimeId(diceId);
    }

    @Override
    public List<DiceBetDto> getLastFourBetInfo(Long timeId, Long userId, Long groupId) {
        return getBaseMapper().getLastFourBetInfo(timeId, userId, groupId);
    }
}
