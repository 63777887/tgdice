package com.jwk.tgdice.enums;


public enum StatusE {

    /**
     * 删除
     */
    Delete((byte) 0, "结束"),
    /**
     * 正常
     */
    Normal((byte) 1, "正常"),
    /**
     * 封盘
     */
    FengPan((byte) 2, "封盘");

    private final Byte id;
    private final String name;

    StatusE(Byte id, String name) {
        this.id = id;
        this.name = name;
    }

    public static StatusE getById(Byte id) {
        if (id == null) {
            return null;
        }
        for (StatusE e : StatusE.values()) {
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
        for (StatusE enums : StatusE.values()) {
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