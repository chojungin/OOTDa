package com.codi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.codi.entity.Auth;
import com.codi.entity.Member;
import com.codi.entity.RoleType;
import com.codi.repository.AuthRepository;
import com.codi.repository.MemberRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class AdminInitializer implements ApplicationRunner {
    
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(ApplicationArguments args) {
    	memberRepository.save(
	        		Member.builder()
		                .account("admin")
		                .password(passwordEncoder.encode("1234"))
		                .userName("관리자")
		                .birthDate("19960116")
		                .roleType(RoleType.ROLE_ADMIN)
		                .useYn("Y")
		                .createdAt(LocalDateTime.now())
		                .build()
                );
    }
}
