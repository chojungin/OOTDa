package com.codi.service;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codi.controller.AuthController;
import com.codi.dto.AuthRequest;
import com.codi.dto.AuthResponse;
import com.codi.entity.Auth;
import com.codi.entity.Member;
import com.codi.exception.InvalidTokenException;
import com.codi.exception.LoginFailedException;
import com.codi.exception.MemberNotFoundException;
import com.codi.exception.TokenNotFoundException;
import com.codi.repository.AuthRepository;
import com.codi.repository.MemberRepository;
import com.codi.security.CustomUserDetails;
import com.codi.security.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
	
	private final MemberRepository memberRepository;
	private final AuthRepository authRepository;
	private final PasswordEncoder encoder;
	private final TokenProvider tokenProvider;
	
	/**
     * 로그인 비즈니스 로직
     */
	public AuthResponse login(AuthRequest request) throws LoginFailedException, MemberNotFoundException {
		
		String account = request.getAccount();
		String password = request.getPassword();
		
		Member member = memberRepository.findByAccount(account).orElseThrow(
				() -> new MemberNotFoundException("존재하지 않는 아이디입니다. \n account : "+account) //로그인 아이디가 존재하지 않을 때
			);
		
			//로그인 아이디가 존재할 때
			if (encoder.matches(password, member.getPassword())) { //비밀번호가 일치할 때
				
				//회원 정보와 회원 패스워드로 토큰 생성
				String accessToken = tokenProvider.createAccessToken(
						new UsernamePasswordAuthenticationToken(new CustomUserDetails(member), member.getPassword()));
				String refreshToken = tokenProvider.createRefreshToken(
						new UsernamePasswordAuthenticationToken(new CustomUserDetails(member), member.getPassword()));
				
				Auth auth = Optional.ofNullable(authRepository.findAuthByMember(member))
				        	.map(updateAuth -> {
				                //권한 정보가 존재할 때 업데이트
				        		updateAuth.updateAccessToken(accessToken);
				        		updateAuth.updateRefreshToken(refreshToken);
				                return updateAuth;
				            })
				        	.orElseGet(() -> {
				                //권한 정보가 존재하지 않을 때 인서트
				                Auth registAuth = authRepository.save(
				                        Auth.builder()
				                            .member(member)
				                            .tokenType("Bearer")
				                            .accessToken(accessToken)
				                            .refreshToken(refreshToken)
				                            .build()
				                );
				                return registAuth;
				            });
				return new AuthResponse(auth, "로그인에 성공하였습니다.");
				
			} else {
				//비밀번호가 불일치할 때
				throw new LoginFailedException("존재하지 않는 아이디이거나 아이디 또는 비밀번호를 잘못 입력했습니다.");
			}
	}
	
	@Transactional
	public String refreshToken(String refreshToken) throws TokenNotFoundException, InvalidTokenException{
		//리프레시 토큰이 유효한 경우 권한 정보로 새로운 액세스 토큰을 생성하여 업데이트
		log.info("************findMemberById refreshToken : "+refreshToken);
		if (tokenProvider.isValidateToken(refreshToken)) {
			Auth auth = authRepository.findAuthByRefreshToken(refreshToken).orElseThrow(() -> {
    			throw new TokenNotFoundException("refresh_token을 찾을 수 없습니다.");
    		});
			String newAccessToken = tokenProvider.createAccessToken(
			        new UsernamePasswordAuthenticationToken(
			                new CustomUserDetails(auth.getMember()), auth.getMember().getPassword()));
			auth.updateAccessToken(newAccessToken);
			return newAccessToken;
		} else {
			throw new InvalidTokenException("refresh_token이 유효하지 않습니다.");
		}
	}
	
}
