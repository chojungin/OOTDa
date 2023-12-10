package com.codi.dto;

import com.codi.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MemberResponse {
	
	private Long id;
	private String account;
	private String password;
	private String userName;
	private String birthDate;
	private String roleType;
	private String createdAt;
	private String useYn;
	
	@Builder
	public MemberResponse(Member member) {
		this.id = member.getId();
		this.account = member.getAccount();
		this.password = member.getPassword();
		this.userName = member.getUserName();
		this.birthDate = member.getBirthDate();
		this.roleType = member.getRoleType().name();
		this.createdAt = member.getCreatedAt().toString();
		this.useYn = member.getUseYn();
	}
	
}
