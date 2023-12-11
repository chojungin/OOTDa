package com.codi.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.codi.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Repository
@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, Long>{
	
	//회원 정보 조회
	public Member findMemberById(Long id);
	
	//로그인 아이디로 회원 정보 조회
	public Optional<Member> findByAccount(String account);

}
