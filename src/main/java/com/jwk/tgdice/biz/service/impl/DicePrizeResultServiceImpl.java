package com.jwk.tgdice.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwk.tgdice.biz.dao.DicePrizeResultMapper;
import com.jwk.tgdice.biz.entity.DicePrizeResult;
import com.jwk.tgdice.biz.service.DicePrizeResultService;
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
public class DicePrizeResultServiceImpl extends
        ServiceImpl<DicePrizeResultMapper, DicePrizeResult> implements DicePrizeResultService {

    @Override
    public List<DiceBetDto> getPrizeBet(Long timeId, Long userId) {
        return baseMapper.getPrizeBet(timeId, userId);
    }
}
