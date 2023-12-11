package com.codi.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codi.dto.AuthResponse;
import com.codi.dto.MemberRequest;
import com.codi.exception.LoginFailedException;
import com.codi.exception.MemberNotFoundException;
import com.codi.service.AuthService;
import com.codi.service.MemberService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

import com.codi.dto.AuthRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController 
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody AuthRequest request) {
        
    	log.info("*********Login*********");

    	try {
    		AuthResponse response = authService.login(request);
    		return ResponseEntity.status(HttpStatus.OK)
    				.header(HttpHeaders.LOCATION, "/setting")
    				.body(response);
    		
        } catch (LoginFailedException e) {
    	    //로그인 실패 예외 처리
    	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    	    		.body(new AuthResponse(e.getMessage()));
    	    
    	} catch (MemberNotFoundException e) {
    	    //아이디가 존재하지 않는 예외 처리
    	    return ResponseEntity.status(HttpStatus.NOT_FOUND)
    	    		.body(new AuthResponse(e.getMessage()));
    	    
    	} catch (Exception e) {
    	    //기타 예외 처리
    		log.error("Login Error : " + e);
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    }
    
    @GetMapping("/duplicateCheck")
	public String duplicateCheck (@RequestParam String account) {
		
    	try {
    		return memberService.validateDuplicateMember(account)
    				? "사용가능한 아이디입니다." : "이미 사용중인 아이디입니다.";
			
		} catch (Exception e) {
			log.error("duplicateCheck Error : " + e);
			return "아이디 중복체크에 실패하였습니다.";
		}
	}
	
	@PostMapping("/join")
	public ResponseEntity<String> join (@RequestBody MemberRequest request) {
		
		log.info("*********Join*********");
		
		try {
			memberService.saveMember(request);
			return ResponseEntity.status(HttpStatus.OK)
	                .header(HttpHeaders.LOCATION, "/login")
	                .body("회원가입에 성공하였습니다.");
			
		} catch (Exception e) {
			log.error("Join Error : " + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("회원가입에 실패하였습니다.");
		}
			
	}
	
	@GetMapping("/refresh")
    public ResponseEntity<?> refreshToken (@RequestHeader("REFRESH_TOKEN") String refreshToken) {
		
		try {
			String newAccessToken = authService.refreshToken(refreshToken);
	        return ResponseEntity.status(HttpStatus.OK).body(newAccessToken);
	        
		} catch (Exception e) {
			log.error("refreshToken Error : " + e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401 RefreshToken이 유효하지 않은 상태(로그아웃 처리)
		}
    }
	   
}
