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
	
	private String account;
	private String userName;
	private String birthDate;
	private String roleType;
	
	@Builder
	public MemberResponse(Member member) {
		this.account = member.getAccount();
		this.userName = member.getUserName();
		this.birthDate = member.getBirthDate();
		this.roleType = member.getRoleType().name();
	}
	
}
