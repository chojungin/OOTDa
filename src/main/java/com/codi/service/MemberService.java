package com.codi.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codi.dto.MemberRequest;
import com.codi.dto.MemberResponse;
import com.codi.entity.Member;
import com.codi.entity.RoleType;
import com.codi.exception.InvalidTokenException;
import com.codi.repository.MemberRepository;
import com.codi.security.TokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	
	private final TokenProvider tokenProvider;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	/**
     * 회원 정보 조회
	 * @throws Exception 
     */
    public MemberResponse findMemberById (String accessToken) throws InvalidTokenException{
    	//액세스 토큰이 유효한 경우 회원 고유 아이디 추출하여 회원 정보 조회
    	if (tokenProvider.isValidateToken(accessToken)) {
    		Long id = tokenProvider.getUserIdFromToken(accessToken.substring(7));
        	Member member = Optional.ofNullable(memberRepository.findMemberById(id)).orElseThrow(
    							() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다. \n member_id : " + id.toString()));
        	return new MemberResponse(member);
    	} else {
    		throw new InvalidTokenException("access_token이 유효하지 않습니다.");
    	}
	}
	
    /**
     * 로그인 아이디로 회원 정보 조회
     */
    public Optional<Member> findByAccount(String account){
        return memberRepository.findByAccount(account);
    }
    
    /**
     * 중복 회원 검증
     */
    public boolean validateDuplicateMember(String account) {
    	return memberRepository.findByAccount(account).isEmpty();
    }
    
    /**
     * 회원 가입
     */
    @Transactional
    public void saveMember(MemberRequest request) {
    	memberRepository.save( //회원 정보 등록
    			Member.builder()
			        .account(request.getAccount())
			        .password(passwordEncoder.encode(request.getPassword()))
			        .userName(request.getUserName())
			        .birthDate(request.getBirthDate())
			        .roleType(RoleType.ROLE_USER)
			        .useYn("Y")
			        .createdAt(LocalDateTime.now())
			        .build()
	        );
    }
    
}
