package com.codi.security;

import java.io.IOException;
import java.util.Optional;



import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codi.service.MemberService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
							//OncePerRequestFilter는 각 HTTP 요청에 대해 한 번만 실행되는 것을 보장한다. 
							//HTTP 요청마다 JWT를 검증하는 것은 비효율적이기 때문에 OncePerRequestFilter를 상속함으로써 
							//JWT 검증을 보다 효율적으로 수행할 수 있다.
	
    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    
    //추출한 토큰 정보를 필터링하는 메소드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    	//(1) Request의 Authorization 값에서 접두어 Bearer 를 확인하고 토큰 값만 추출
    	String accessToken = getTokenFromRequest(request);
    	
    	if (accessToken != null && tokenProvider.isValidateToken(accessToken)) { //권한이 있는 토큰인지 여부 확인
    		
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
    
	//(2) 토큰에서 회원 아이디를 추출하여 확인 후 인증 토큰 발급 (회원정보, 패스워드는 null, 권한)
    private UsernamePasswordAuthenticationToken getAuthenticationFromToken(String token) {
    	Long id = tokenProvider.getUserIdFromToken(token);
        UserDetails userDetails = customUserDetailsService.loadUserByMemberId(id);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    
}