package com.money_transfer_api.app.exception;

public class MessageException extends Exception {

	private static final long serialVersionUID = 1L;

	public MessageException(String msg) {
		super(msg);
	}

	public MessageException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
