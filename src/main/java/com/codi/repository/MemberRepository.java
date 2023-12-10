package com.codi.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.codi.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//데이터베이스와의 상호작용에 대한 내용을 작성

@Repository
@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, Long>{
	
	//회원 정보 조회
	public Member findMemberById(Long id);
	
	//로그인 아이디로 회원 정보 조회
	public Optional<Member> findByAccount(String account);
	
	/*
	@Transactional
	public void save(Member member) {
		em.persist(
			Member.builder()
	            .account(member.getAccount())
	            .password(passwordEncoder.encode(member.getPassword()))
	            .userName(member.getUserName())
	            .birthDate(member.getBirthDate())
	            .roleType(member.getRoleType() == null ? RoleType.ROLE_USER : member.getRoleType())
	            .useYn("Y")
	            .createdAt(LocalDateTime.now())
	            .build()
        );
	}
	*/
}
