package com.codi.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Member{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String account;
	
	@Column(nullable = false)
	private String password;
	
	private String userName;
	
	private String birthDate;
	
	@Enumerated(EnumType.STRING)
	private RoleType roleType; //ROLE_ADMIN, ROLE_USER
	
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private String useYn; //Y, N
	
	@OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
    private Auth auth;
	
	@Builder
	public Member(
		Long id,
		String account, 
		String password, 
		String userName, 
		String birthDate, 
		RoleType roleType,
		LocalDateTime createdAt, 
		String useYn
	) {
		this.id = id;
		this.account = account;
		this.password = password;
		this.userName = userName;
		this.birthDate = birthDate;
		this.roleType = roleType;
		this.createdAt = createdAt;
		this.useYn = useYn;
	}
	
}
