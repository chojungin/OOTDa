package com.codi.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codi.dto.PollRequest;
import com.codi.dto.PollResponse;
import com.codi.service.PollService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/poll")
@RequiredArgsConstructor
@RestController
public class PollController {
	
	private final PollService pollservice; 
	
	@PostMapping("/post")
	public ResponseEntity<?> postOutfitPoll(@RequestBody PollRequest request){
		List<PollResponse> response = pollservice.postPoll(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
