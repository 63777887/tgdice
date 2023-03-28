package com.jwk.tgdice.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwk.tgdice.biz.dao.DiceMapper;
import com.jwk.tgdice.biz.entity.Dice;
import com.jwk.tgdice.biz.service.DiceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiwk
 * @since 2023-03-19
 */
@Service
public class DiceServiceImpl extends ServiceImpl<DiceMapper, Dice> implements DiceService {

}
