package com.codi.exception;

import io.jsonwebtoken.JwtException;

public class TokenNotFoundException extends JwtException {

	private static final long serialVersionUID = 1L;
	
	public TokenNotFoundException(String message) {
		super(message);
	}

	public TokenNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
