package com.jwk.tgdice.enums;


/**
 * @author Jiwk
 * @version 0.1.3
 * <p>
 * 缓存类型
 * @date 2022/11/8
 */
public enum DSEnumsE {

    /**
     * 单
     */
    Dan(1, "单"),
    /**
     * 双
     */
    Shuang(2, "双");

    private final Integer id;
    private final String name;

    DSEnumsE(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static boolean contains(String message) {
        for (DSEnumsE enums : DSEnumsE.values()) {
            if (message.contains(enums.name)) {
                return true;
            }
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
