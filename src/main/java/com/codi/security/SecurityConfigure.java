package com.codi.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfigure {
	
	private final AuthenticationFilter authenticationFilter;
	private CustomAuthenticationEntryPoint authenticationEntryPoint;
	private CustomAccessDeniedHandler accessDeniedHandler;
	
	@Bean //Password Encoder Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Order(0) //우선순위 설정
	@Bean //Request Filter Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		
		http.csrf(csrf -> csrf.disable()) //Cross-Site Request Forgery 비활성화
			.cors(cors -> cors.configurationSource(introspector)) //Cross-Origin Resource Sharing 활성화
			.headers(headers -> headers.frameOptions(option -> option.sameOrigin())) //X-Frame-Options 헤더 설정, 웹 페이지를 iframe으로 삽입하는 공격 방지
			.formLogin(fl -> fl.disable()) //formLogin 비활성화
			.sessionManagement(sc -> sc.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //인증에 사용할 세션을 생성하지 않도록 비활성화
			.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint)) //인증 예외처리
			.exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler)); //인가 예외처리
		
		//인증 권한 필터 설정
		http.authorizeHttpRequests(request -> 
				request
					.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() //누구나 허용
					.requestMatchers(PathRequest.toH2Console()).permitAll() //H2 콘솔 접속은 누구나 허용
					.requestMatchers(
		            		new MvcRequestMatcher(introspector, "/"),
		            		new MvcRequestMatcher(introspector, "/login"),
		            		new MvcRequestMatcher(introspector, "/join"),
		            		new MvcRequestMatcher(introspector, "/setting")
		        		).permitAll() //front 이동 누구나 허용
					.requestMatchers(
		            		new MvcRequestMatcher(introspector, "/api/auth/duplicateCheck"),
		            		new MvcRequestMatcher(introspector, "/api/auth/join"),
		            		new MvcRequestMatcher(introspector, "/api/auth/login"),
		            		new MvcRequestMatcher(introspector, "/api/auth/refresh"),
		            		new MvcRequestMatcher(introspector, "/api/user/get")
		        		).permitAll() //api 호출 누구나 허용
					.requestMatchers(
			            	new MvcRequestMatcher(introspector, "/api/user/put"),
			            	new MvcRequestMatcher(introspector, "/api/user/delete")
						).authenticated() //인증된 사용자 허용
					.requestMatchers(
		            		new MvcRequestMatcher(introspector, "/api/admin/**")
							).hasAuthority("ROLE_ADMIN") //관리자만 허용
					.anyRequest().authenticated()
				);
		
		//jwt 필터를 UsernamePasswordAuthenticationFilter 전에 등록
    	http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
