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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfigure {
	
	private final AuthenticationFilter authenticationFilter;
	
	// DB 드라이버 클래스 이름 (h2 사용 시 security 충돌 해결 위해)
    //@Value("${spring.datasource.driver-class-name}") private String springDatasourceDriverClassName;
    
	@Bean //Password Encoder Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Order(0) //우선순위 설정
	@Bean //Request Filter Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        
		// jwt 필터를 UsernamePasswordAuthenticationFilter 전에 등록
    	http.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class);
		
		http.csrf(csrf -> csrf.disable()) //csrf 비활성화
			//.cors(cors -> cors.disable()) //cors 비활성화
			.formLogin(fl -> fl.disable()) //formLogin 비활성화
			.sessionManagement(sc -> sc.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //JSessionId 비활성화
			.headers(headers -> headers.frameOptions(option -> option.sameOrigin())); //h2 console을 사용하기 위한 설정
		
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
		        		).permitAll() //누구나 허용
					.requestMatchers(
	            		new MvcRequestMatcher(introspector, "/api/auth/duplicateCheck"),
	            		new MvcRequestMatcher(introspector, "/api/auth/join"),
	            		new MvcRequestMatcher(introspector, "/api/auth/login"),
	            		new MvcRequestMatcher(introspector, "/api/auth/refresh"),
	            		new MvcRequestMatcher(introspector, "/api/user/get")
	        		).permitAll() //누구나 허용
					.requestMatchers(
			            	new MvcRequestMatcher(introspector, "/api/user/put"),
			            	new MvcRequestMatcher(introspector, "/api/user/delete")
	        			).authenticated() //인증된 사용자 허용
					.requestMatchers(
		            		new MvcRequestMatcher(introspector, "/api/admin/**")
	        		).hasAuthority("ROLE_ADMIN") //관리자만 허용
					.anyRequest().authenticated()
        );
		
        
        // DB 드라이버 클래스 이름이 h2일 경우, h2 관련 옵션 추가
        //if (springDatasourceDriverClassName.equals("org.h2.Driver")) {
            // h2 관련 옵션
        	//http.headers(hc -> hc.frameOptions(fc -> fc.sameOrigin()));
        //}
        
        return http.build();
    }
}
