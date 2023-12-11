package com.codi.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codi.entity.Member;
import com.codi.repository.MemberRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	
	public UserDetails loadUserByMemberId(Long id) throws IllegalArgumentException {
		
		Member member = memberRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다. \n member_id : "+id.toString())
			);
		return new CustomUserDetails(member);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

}
