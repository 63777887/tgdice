package com.jwk.tgdice.service.impl.playrule;

import com.jwk.tgdice.content.PlayTypeContent;
import com.jwk.tgdice.entity.BetEntity;
import com.jwk.tgdice.enums.DicePrizeEnumsE;
import com.jwk.tgdice.exception.BetMessageException;
import com.jwk.tgdice.service.RuleService;
import com.jwk.tgdice.util.DiceBetUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 对子玩法
 * @date 2023/3/16
 */
@Service
public class DuiZiRuleService implements RuleService {

    @Override
    public List<BetEntity> onMessage(StringBuilder message, Update update) throws BetMessageException {
        List<BetEntity> betEntities = new ArrayList<>();
        if (!support(message.toString())) {
            return betEntities;
        }
        Matcher matcher = PlayTypeContent.PATTERN_DUIZI.matcher(message.toString());
        while (matcher.find()) {
            String findStr = matcher.replaceFirst("");
            String play = matcher.group(1);
            Integer amount = Integer.parseInt(matcher.group(2));
            if (amount < 10) {
                return new ArrayList<>();
            }
            for (DicePrizeEnumsE prizeEnumsE : DicePrizeEnumsE.values()) {
                if (DiceBetUtil.strEquals(prizeEnumsE.getValue(), play)) {
                    BetEntity betEntity = new BetEntity(null, prizeEnumsE.getCode(), PlayTypeContent.PLAY_TYPE_DUIZI, amount);
                    betEntities.add(betEntity);
                }
            }
            message.delete(0, message.length()).append(findStr);
            matcher = PlayTypeContent.PATTERN_DUIZI.matcher(message);
        }
        return betEntities;
    }

    @Override
    public boolean support(String message) {

        return PlayTypeContent.PATTERN_DUIZI.matcher(message).find();
    }

    @Override
    public int order() {
        return 3;
    }

}

