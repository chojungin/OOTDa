package com.codi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codi.dto.PollRequest;
import com.codi.dto.PollResponse;
import com.codi.entity.Item;
import com.codi.entity.Poll;
import com.codi.entity.PollItem;
import com.codi.repository.ItemRepository;
import com.codi.repository.PollItemRepository;
import com.codi.repository.PollRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PollService {
	
	private final PollRepository pollRepository;
	private final PollItemRepository pollItemRepository;
	private final ItemRepository itemRepository;
	
	@Transactional
	public List<PollResponse> postPoll(PollRequest request) {
		
		Poll poll = pollRepository.save(
			Poll.builder()
				.city(request.getCity())
				.district(request.getDistrict())
				.sensTemp(request.getSensTemp())
				.createdAt(LocalDateTime.now())
				.build()
		);
		
		for (Long itemId : request.getItemIdList()) {
			
			Item item = itemRepository.findById(itemId).orElseThrow(
	        		()-> new EntityNotFoundException("존재하지 않는 아이템입니다. \n itemId : " + itemId));
			
			pollItemRepository.save(
				PollItem.builder()
					.item(item)
					.poll(poll)
					.build()
			);
	    }
		//투표 결과 보여주기
		List<PollResponse> response = pollItemRepository.getPollResult(poll.getCity(), poll.getDistrict());
		return response;
	}

}
