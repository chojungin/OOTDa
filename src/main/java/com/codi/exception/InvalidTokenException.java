package com.codi.exception;

import io.jsonwebtoken.JwtException;

public class InvalidTokenException extends JwtException {

	private static final long serialVersionUID = 1L;
	
	public InvalidTokenException(String message) {
		super(message);
	}

	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
	}

}
