package com.codi.exception;

public class MemberNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public MemberNotFoundException() {
		super();
	}

	public MemberNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MemberNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemberNotFoundException(String message) {
		super(message);
	}

	public MemberNotFoundException(Throwable cause) {
		super(cause);
	}

}
