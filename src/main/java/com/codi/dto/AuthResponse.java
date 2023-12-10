package com.codi.dto;

import com.codi.entity.Auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthResponse {
		
	private String tokenType;
	private String accessToken;
    private String refreshToken;
    private String userName;
    private String roleType;
    private String message;
    
	@Builder
	public AuthResponse(Auth auth, String message) {
		this.tokenType = auth.getTokenType();
		this.accessToken = auth.getAccessToken();
		this.refreshToken = auth.getRefreshToken();
		this.userName = auth.getMember().getUserName();
		this.roleType = auth.getMember().getRoleType().name();
		this.message = message;
	}
	
	@Builder
	public AuthResponse(String message) {
		this.message = message;
	}
    
}
