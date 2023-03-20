package com.jwk.tgdice.content;

import java.util.regex.Pattern;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * 玩法正则
 * @date 2023/3/17
 */
public interface PlayTypeContent {

    String REGEX_BAOZI = "(豹子)(\\d{1,9})";
    String REGEX_DUIZI = "(对子)(\\d{1,9})";
    String REGEX_SHUNZI = "(顺子)(\\d{1,9})";
    String REGEX_LONG = "(龙|虎|合)(\\d{1,9})";
    String REGEX_POINT_KILL = "\\d+/\\d+杀\\d{1,9}";
    String REGEX_POINT = "\\d{1,9}";
    String REGEX_AMOUNT = "\\d{1,9}";
    String REGEX_SINGLE_BET = "(大|小|单|双)(\\d{1,9})";
    String REGEX_COMPOSITE_BET = "(大|小)(单|双)(\\d{1,9})";
    String REGEX_DREAM_BET = "(\\d{1})(豹)(\\d{1,9})";
    String REGEX_SHA_BET = "(\\d{1,2})(杀)(\\d{1,9})";
    String REGEX_DRAGON_TIGER_BET = "(龙|虎|合)(\\d{1,9})";

    Pattern PATTERN_BAOZI = Pattern.compile(REGEX_BAOZI);
    Pattern PATTERN_SHA = Pattern.compile(REGEX_SHA_BET);
    Pattern PATTERN_DUIZI = Pattern.compile(REGEX_DUIZI);
    Pattern PATTERN_SHUNZI = Pattern.compile(REGEX_SHUNZI);
    Pattern PATTERN_LONG = Pattern.compile(REGEX_LONG);
    Pattern PATTERN_POINT_KILL = Pattern.compile(REGEX_POINT_KILL);
    Pattern PATTERN_POINT = Pattern.compile(REGEX_POINT);
    Pattern PATTERN_AMOUNT = Pattern.compile(REGEX_AMOUNT);
    Pattern PATTERN_SINGLE_BET = Pattern.compile(REGEX_SINGLE_BET);
    Pattern PATTERN_COMPOSITE_BET = Pattern.compile(REGEX_COMPOSITE_BET);
    Pattern PATTERN_DREAM_BET = Pattern.compile(REGEX_DREAM_BET);
    Pattern PATTERN_DRAGON_TIGER_BET = Pattern.compile(REGEX_DRAGON_TIGER_BET);


    String PLAY_TYPE_SINGLE = "单式玩法";

    String PLAY_TYPE_COMPOSITE = "复式玩法";

    String PLAY_TYPE_SHA = "点杀玩法";

    String PLAY_TYPE_DREAM = "梦幻玩法";

    String PLAY_TYPE_BAOZI = "豹子玩法";
    String PLAY_TYPE_DUIZI = "对子玩法";
    String PLAY_TYPE_SHUNZI = "顺子玩法";
    String PLAY_TYPE_LONG = "龙虎玩法";


}
