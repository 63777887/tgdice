package com.jwk.tgdice.exception;

/**
 * @author Jiwk
 * @version 0.1.3
 * <p>
 * 异常处理
 * @date 2022/11/2
 */
public class BetMessageException extends Exception {

	private static final long serialVersionUID = 1L;


	public BetMessageException(String msg) {
		super(msg);
	}

	public BetMessageException() {
		super("错误文本");
	}



	public BetMessageException(Throwable cause) {
		super(cause);
	}

	public BetMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public BetMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
