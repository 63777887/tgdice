package com.jwk.tgdice;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * t
 * @date 2023/3/17
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    private static final String REGEX_BAOZI = "(豹)(\\d{1,3})";
    private static final String REGEX_DUIZI = "对\\d{1,3}";
    private static final String REGEX_SHUNZI = "顺\\d{1,3}";
    private static final String REGEX_POINT_KILL = "\\d+/\\d+杀\\d{1,3}";
    private static final String REGEX_POINT = "\\d{1,2}";
    private static final String REGEX_AMOUNT = "\\d{2,4}";
    private static final String REGEX_SINGLE_BET = "(大|小|单|双)(\\d{2,4})";
    private static final String REGEX_COMPOSITE_BET = "(大|小)(单|双)(\\d{2,4})";
    private static final String REGEX_DREAM_BET = "(\\d)(豹)(\\d{2,4})";
    private static final String REGEX_SHA_BET = "(\\d)(杀)(\\d{2,4})";
    private static final String REGEX_DRAGON_TIGER_BET = "(龙|虎|合)(\\d{2,4})";

    private static final Pattern PATTERN_BAOZI = Pattern.compile(REGEX_BAOZI);
    private static final Pattern PATTERN_SHA = Pattern.compile(REGEX_SHA_BET);
    private static final Pattern PATTERN_DUIZI = Pattern.compile(REGEX_DUIZI);
    private static final Pattern PATTERN_SHUNZI = Pattern.compile(REGEX_SHUNZI);
    private static final Pattern PATTERN_POINT_KILL = Pattern.compile(REGEX_POINT_KILL);
    private static final Pattern PATTERN_POINT = Pattern.compile(REGEX_POINT);
    private static final Pattern PATTERN_AMOUNT = Pattern.compile(REGEX_AMOUNT);
    private static final Pattern PATTERN_SINGLE_BET = Pattern.compile(REGEX_SINGLE_BET);
    private static final Pattern PATTERN_COMPOSITE_BET = Pattern.compile(REGEX_COMPOSITE_BET);
    private static final Pattern PATTERN_DREAM_BET = Pattern.compile(REGEX_DREAM_BET);
    private static final Pattern PATTERN_DRAGON_TIGER_BET = Pattern.compile(REGEX_DRAGON_TIGER_BET);

    public static void main(String[] args) {
//                    String input = "大100大单100豹100对子10015杀100";
//                    Pattern pattern = Pattern.compile("((大|小|单|双)\\d+)|((大单|小双|大双|小单)\\d+)|(豹\\d+)|(对子\\d+)|(顺子\\d+)|((\\d+杀|龙)\\d+)|(\\d+点\\d+)|((\\d豹)+\\d+)|(龙\\d+)|(虎\\d+)|(合\\d+)");
//                    Matcher matcher = pattern.matcher(input);
//
//                    while (matcher.find()) {
//                        System.out.println(matcher.group());
//                    }


        String betString = "大100大单100豹666对33顺456点杀5/16杀1002豹100龙100";
        Matcher matcher;
        String play;
        int amount;
        // 单式
        matcher = PATTERN_SINGLE_BET.matcher(betString);
        while (matcher.find()) {
            System.out.printf("单式玩法");
            play = matcher.group(1);
            amount = Integer.parseInt(matcher.group(2));
            System.out.printf(" bet: %s %d%n", play, amount);
        }
         // 复式
        matcher = PATTERN_COMPOSITE_BET.matcher(betString);
        while (matcher.find()) {
            System.out.printf("复式玩法");
            play = matcher.group(1) + matcher.group(2);
            amount = Integer.parseInt(matcher.group(3));
            System.out.printf(" bet: %s %d%n", play, amount);
        }
        // 梦幻玩法
        matcher = PATTERN_DREAM_BET.matcher(betString);
        while (matcher.find()) {
            String bei = matcher.group(1);
            if ("0".equals(bei)){
                System.out.printf("豹子玩法");
                play = matcher.group(2);
                amount = Integer.parseInt(matcher.group(3));
                System.out.printf(" bet: %s %d%n", play, amount);
            }else {
                System.out.printf("梦幻玩法");
                play = matcher.group(2);
                amount = Integer.parseInt(matcher.group(3));
                System.out.printf(" bet: %s %s %d%n", bei, play, amount);
            }

        }
        // 点杀玩法
        matcher = PATTERN_SHA.matcher(betString);
        while (matcher.find()) {
            System.out.printf("点杀玩法");
            String bei = matcher.group(1);
            play = matcher.group(2);
            amount = Integer.parseInt(matcher.group(3));
            System.out.printf(" bet: %s %s %d%n", bei, play, amount);
        }
    }
}


//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class BetParser {
//    private static final String REGEX_BAOZI = "豹\\d{1,3}";
//    private static final String REGEX_DUIZI = "对\\d{1,3}";
//    private static final String REGEX_SHUNZI = "顺\\d{1,3}";
//    private static final String REGEX_POINT_KILL = "\\d+/\\d+杀\\d{1,3}";
//    private static final String REGEX_POINT = "\\d{1,2}";
//    private static final String REGEX_AMOUNT = "\\d{2,4}";
//    private static final String REGEX_SINGLE_BET = "(大|小|单|双)(\\d{2,4})";
//    private static final String REGEX_COMPOSITE_BET = "(大|小)(单|双)(\\d{2,4})";
//    private static final String REGEX_DREAM_BET = "(\\d)(豹)(\\d{2,4})";
//    private static final String REGEX_DRAGON_TIGER_BET = "(龙|虎|合)(\\d{2,4})";
//
//    private static final Pattern PATTERN_BAOZI = Pattern.compile(REGEX_BAOZI);
//    private static final Pattern PATTERN_DUIZI = Pattern.compile(REGEX_DUIZI);
//    private static final Pattern PATTERN_SHUNZI = Pattern.compile(REGEX_SHUNZI);
//    private static final Pattern PATTERN_POINT_KILL = Pattern.compile(REGEX_POINT_KILL);
//    private static final Pattern PATTERN_POINT = Pattern.compile(REGEX_POINT);
//    private static final Pattern PATTERN_AMOUNT = Pattern.compile(REGEX_AMOUNT);
//    private static final Pattern PATTERN_SINGLE_BET = Pattern.compile(REGEX_SINGLE_BET);
//    private static final Pattern PATTERN_COMPOSITE_BET = Pattern.compile(REGEX_COMPOSITE_BET);
//    private static final Pattern PATTERN_DREAM_BET = Pattern.compile(REGEX_DREAM_BET);
//    private static final Pattern PATTERN_DRAGON_TIGER_BET = Pattern.compile(REGEX_DRAGON_TIGER_BET);
//
//    public static void main(String[] args) {
//        String betString = "大100大单100豹666对33顺456点杀5/16杀1002豹100龙100";
//        Matcher matcher;
//        String play;
//        int amount;
//        matcher = PATTERN_SINGLE_BET.matcher(betString);
//        while (matcher.find()) {
//            play = matcher.group(1);
//            amount = Integer.parseInt(matcher.group(2));
//            System.out.printf("Single bet: %s %d%n", play, amount);
//        }
//        matcher = PATTERN_COMPOSITE_BET.matcher(betString);
//        while (matcher.find()) {
//            play = matcher.group(1) + matcher.group(2);
//            amount = Integer.parseInt(matcher.group(3));
//            System.out.printf("Composite bet: %s %d%n", play, amount);
//        }
//        matcher = PATTERN_DREAM_BET.matcher(betString);
//        while (matcher.find()) {
//            play = matcher.group(2


