package com.codi.security;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@PropertySource("classpath:jwt.yml")
public class TokenProvider {
	
	@Value("${secretKey}")
    private String secretKey;
    @Value("${accessTokenExpirationTime}")
    private Long accessTokenExpirationTime;
    @Value("${refreshTokenExpirationTime}")
    private Long refreshTokenExpirationTime;
    
     
    //액세스 토큰을 생성하는 메소드
    public String createAccessToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername()) //토큰 정보1
                .claim("member_id", customUserDetails.getId()) //토큰 정보2
                .setIssuedAt(Date.from(Instant.now())) //토큰 발행 시간
                .setExpiration(Date.from(Instant.now().plus(accessTokenExpirationTime, ChronoUnit.HOURS))) //토큰 만료 시간
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName())) //HS512 알고리즘 서명
                .compact();
    }
    
    //리프레시 토큰을 생성하는 메소드
    public String createRefreshToken(Authentication authentication) {
    	CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    	return Jwts.builder()
    			.setSubject(customUserDetails.getUsername()) //토큰 정보1
                .claim("member_id", customUserDetails.getId()) //토큰 정보2
                .setIssuedAt(Date.from(Instant.now())) //토큰 발행 시간
                .setExpiration(Date.from(Instant.now().plus(refreshTokenExpirationTime, ChronoUnit.HOURS))) //토큰 만료 시간
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName())) //HS512 알고리즘 서명
                .compact();
    }
    
    //토큰 유효성 검증
    public Boolean isValidateToken(String token) throws UnsupportedJwtException, MalformedJwtException, SignatureException, ExpiredJwtException, IllegalArgumentException{
        try {
        	log.info("isValidateToken::::::"+token);
            Jwts.parserBuilder()
	            .setSigningKey(secretKey.getBytes())
	            .build()
	            .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException e) { 
        	log.error("isValidateToken Error : Unsupported JWT token");
        } catch (MalformedJwtException e) {
        	log.error("isValidateToken Error : Invalid JWT token");
        } catch (SignatureException e) {
        	log.error("isValidateToken Error : Invalid JWT signature");
        } catch (ExpiredJwtException e) {
        	log.error("isValidateToken Error : Expired JWT token");
        } catch (IllegalArgumentException e) {
        	log.error("isValidateToken Error : JWT claims string is empty.");
        } catch (Exception e) {
        	log.error("isValidateToken Error : " + e);
        }
        return false;
    }
    
    //토큰을 분석하여 아이디를 반환
    public Long getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
        		.setSigningKey(secretKey.getBytes())
        		.build()
        		.parseClaimsJws(token)
                .getBody()
                .get("member_id", Long.class);
    }
    
    //토큰을 분석하여 이름을 반환
    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder()
        		.setSigningKey(secretKey.getBytes())
        		.build()
        		.parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    //토큰을 분석하여 만료시간을 반환
    public Date getExpirationFromToken(String token) {
        return Jwts.parserBuilder()
        		.setSigningKey(secretKey.getBytes())
        		.build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}