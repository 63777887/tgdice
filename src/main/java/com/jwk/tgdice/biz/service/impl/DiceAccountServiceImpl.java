package com.jwk.tgdice.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwk.tgdice.biz.dao.DiceAccountMapper;
import com.jwk.tgdice.biz.entity.DiceAccount;
import com.jwk.tgdice.biz.service.DiceAccountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户表 服务实现类
 * </p>
 *
 * @author jiwk
 * @since 2023-03-23
 */
@Service
public class DiceAccountServiceImpl extends ServiceImpl<DiceAccountMapper, DiceAccount> implements
        DiceAccountService {

}
