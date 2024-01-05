package com.codi.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PollRequest {
	
	private String city;
	private String district;
	private int sensTemp;
	private List<Long> itemIdList;
	
	@Builder
	public PollRequest(
		String city, 
		String district, 
		String sensTemp, 
		List<Integer> itemIdList
	) {
		this.city = city;
		this.district = district;
		this.sensTemp = Integer.parseInt(sensTemp);
		this.itemIdList = itemIdList.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
	}
}
