package com.codi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PollResponse {
	
	private Long itemId;
	private String itemName;
	private Long itemCount;
	private String pollDate;
	
	@Builder
	public PollResponse(
		Long itemId, 
		String itemName,
		Long itemCount,
		String pollDate
	) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCount = itemCount;
		this.pollDate = pollDate;
	}
	
}
