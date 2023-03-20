package com.jwk.tgdice.content;

import com.jwk.tgdice.biz.entity.DicePlayType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 系统缓存
 * @date 2023/3/18
 */
public class DiceCaCheContent {
    public static Map<String, DicePlayType> dicePlayTypeTypeCache = new HashMap<>();
    public static List<Long> groupCache = new ArrayList<>();

}
