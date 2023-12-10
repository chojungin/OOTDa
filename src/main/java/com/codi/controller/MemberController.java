package com.codi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codi.dto.MemberRequest;
import com.codi.dto.MemberResponse;
import com.codi.service.MemberService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//HTTP 요청을 처리하고 비즈니스 로직을 호출하는 내용을 작성

@Slf4j
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController 
public class MemberController { 
	
	private final MemberService memberService;
	
	@GetMapping("/get")
    public ResponseEntity<MemberResponse> getMember(@RequestHeader("Authorization") String accessToken) {
		try {
			MemberResponse response = memberService.findMemberById(accessToken);
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);
		} catch (Exception e) {
			log.error("getMember Error : " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
	
	@PutMapping("/put")
    public ResponseEntity<MemberResponse> putMember(@RequestHeader("Authorization") String accessToken,
    												@RequestBody MemberRequest request) {
		try {
			MemberResponse response = memberService.findMemberById(accessToken);
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);
		} catch (Exception e) {
			log.error("putMember Error : " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
	
	@PutMapping("/delete")
    public ResponseEntity<?> deleteMember(@RequestHeader("Authorization") String accessToken) {
		try {
			memberService.findMemberById(accessToken);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			log.error("putMember Error : " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }

}
