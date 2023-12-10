package com.codi.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Order(0) //의존성 주입 우선순위를 설정
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
	
    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    
    //추출한 토큰 정보를 필터링하는 메소드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
    	//(1) Request의 Authorization 값에서 접두어 Bearer 를 확인하고 토큰 값만 추출
    	String accessToken = getTokenFromRequest(request);
    	
    	if (accessToken != null && tokenProvider.isValidateToken(accessToken)) { //권한이 있는 토큰인지 여부 확인
    		
    		log.info("*********Token Filtering*********");
    		
    		//(2) 토큰에서 정보를 추출하여 확인 후 인증 토큰으로 반환하고 인증 정보를 등록
        	UsernamePasswordAuthenticationToken authentication = getAuthenticationFromToken(accessToken);
        	authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        	
        	//(3) Security Context에 인증 정보를 설정
        	SecurityContextHolder.getContext().setAuthentication(authentication);
    	}
    	
        filterChain.doFilter(request, response);
    }
    
    //(1) Request의 Authorization 값에서 접두어 Bearer 를 확인하고 토큰 값만 추출
    private String getTokenFromRequest (HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.startsWith("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }
    
	//(2) 토큰에서 회원 아이디를 추출하여 확인 후 인증 토큰으로 반환 (회원정보, 패스워드, 권한)
    private UsernamePasswordAuthenticationToken getAuthenticationFromToken(String token) {
        Long id = tokenProvider.getUserIdFromToken(token);
        UserDetails userDetails = customUserDetailsService.loadUserByMemberId(id);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    
}