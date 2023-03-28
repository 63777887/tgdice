package com.jwk.tgdice.enums;


public enum IsPrizeEnumsE {

    /**
     * 中奖
     */
    IsPrize((byte) 1, "中奖"),
    /**
     * 未中奖
     */
    NoPrize((byte) 2, "未中奖");

    private final Byte id;
    private final String name;

    IsPrizeEnumsE(Byte id, String name) {
        this.id = id;
        this.name = name;
    }

    public static IsPrizeEnumsE getById(Byte id) {
        if (id == null) {
            return null;
        }
        for (IsPrizeEnumsE e : IsPrizeEnumsE.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据Code获取Value
     *
     * @param code 键
     * @return 值
     */
    public static String getDescByCode(Byte code) {
        for (IsPrizeEnumsE enums : IsPrizeEnumsE.values()) {
            if (enums.id.equals(code)) {
                return enums.name;
            }
        }
        return "";
    }

    public Byte getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}