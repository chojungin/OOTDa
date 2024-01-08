package com.codi.controller;


import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codi.dto.PollRequest;
import com.codi.dto.PollResponse;
import com.codi.entity.Poll;
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
	public ResponseEntity<List<PollResponse>> postOutfitPoll(@RequestBody PollRequest request){
		
		log.info("*********postOutfitPoll*********");
		
		try {
			
			Map<String, String> postPoll = pollservice.postPoll(request);
			
			String city = postPoll.get("city");
			String district = postPoll.get("district");
			
			//투표 결과
			List<PollResponse> getPoll = pollservice.getPoll(city, district);
			return ResponseEntity.status(HttpStatus.OK).body(getPoll);
		
		} catch (Exception e) {
    	
			log.error("postOutfitPoll Error : " + e);
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    	}
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<PollResponse>> getOutfitPoll(@RequestParam("city") String city,
															@RequestParam("district") String district){
		
		log.info("*********getOutfitPoll*********");
		
		try {
			
			List<PollResponse> response = pollservice.getPoll(city, district);
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		} catch (Exception e) {
			
			log.error("getOutfitPoll Error : " + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
