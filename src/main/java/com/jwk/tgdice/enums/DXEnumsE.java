package com.jwk.tgdice.enums;


/**
 * @author Jiwk
 * @version 0.1.3
 * <p>
 * 缓存类型
 * @date 2022/11/8
 */
public enum DXEnumsE {

	/** 大 */
	Xiao(1, "大"),
	/** 小 */
	Da(2, "小");

	DXEnumsE(Integer id, String name) {
		this.id = id;
		this.name = name;
	}



	public static boolean contains(String message) {
		for (DXEnumsE enums : DXEnumsE.values()) {
			if (message.contains(enums.name)) {
				return true;
			}
		}
		return false;
	}

	private Integer id;

	private String name;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
