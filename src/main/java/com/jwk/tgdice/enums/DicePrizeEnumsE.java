package com.jwk.tgdice.enums;


/**
 * @author Jiwk
 * @version 0.1.3
 * <p>
 * 缓存类型
 * @date 2022/11/8
 */
public enum DicePrizeEnumsE {

	/** 单 */
	Dan( "dan","单"),
	/** 双 */
	Shuang( "shuang","双"),
	/** 大 */
	Da( "da","大"),
	/** 小 */
	Xiao( "xiao","小"),
	/** 小单 */
	XiaoDan( "xiaodan","小单"),
	/** 小双 */
	XiaoShuang( "xiaoshuang","小双"),
	/** 大单 */
	DaDan( "dadan","大单"),
	/** 大双 */
	DaShuang( "dashuang","大双"),
	/** 对子 */
	DuiZi( "duizi","对子"),
	/** 顺子 */
	ShunZi( "shunzi","顺子"),
	/** 豹子 */
	BaoZi( "baozi","豹子"),
	/** 龙 */
	Long( "long","龙"),
	/** 虎 */
	Hu( "hu","虎"),
	/** 合 */
	He( "he","合"),
	/** 点杀 */
	Sha( "sha","点杀"),
	/** 杂六 */
	Za( "za","杂六"),
	/** 梦幻 */
	Meng( "meng","梦幻");

	DicePrizeEnumsE(String code, String value) {
		this.code = code;
		this.value = value;
	}


	public static DicePrizeEnumsE getByCode(String code) {
		if (code == null) {
			return null;
		}
		for (DicePrizeEnumsE e : DicePrizeEnumsE.values()) {
			if (e.getCode().equals(code)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 根据errCode获取errMsg
	 * @param code 键
	 * @return 值
	 */
	public static String getValueByCode(String code) {
		for (DicePrizeEnumsE enums : DicePrizeEnumsE.values()) {
			if (enums.getCode().equals(code)) {
				return enums.getValue();
			}
		}
		return "";
	}
	private String code;
	private String value;

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}


}
