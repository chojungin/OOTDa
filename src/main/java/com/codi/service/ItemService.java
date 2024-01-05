package com.codi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codi.entity.Item;
import com.codi.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	public List<Item> getItems(String temp) {
		
		int val = Integer.parseInt(temp);
		List<Item> items = itemRepository.findItemByTemperature(val);
		//List<Temperature> temperatures = temperatureRepository.findItemByTemperature(val);
		//List<Item> items = temperatures.stream()
			                //.flatMap(temperature -> temperature.getItems().stream())
			                //.collect(Collectors.toList());
		return items;
	}
	
}
