package com.codi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.codi.entity.Auth;
import com.codi.entity.Member;

@Repository
@EnableJpaRepositories
public interface AuthRepository extends JpaRepository<Auth, Long>{
	
	//권한 정보 조회
	//public Auth findAuth(Long id);
	
	//회원 정보로 권한 정보 조회
	public Auth findAuthByMember(Member member);
	
	//리프레시 토큰으로 권한 정보 조회
	public Optional<Auth> findAuthByRefreshToken(String refreshToken);
	
}
