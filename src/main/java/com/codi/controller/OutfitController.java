package com.codi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codi.entity.Item;
import com.codi.entity.Outfit;
import com.codi.service.OutfitService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/outfit")
@RequiredArgsConstructor
@RestController 
public class OutfitController {
	
	private final OutfitService outfitService;
	
	@GetMapping("/get")
	public List<String> getOutfit(@RequestHeader String temp) {
		
		Outfit outfit = outfitService.getOutfitInfo(temp);
		List<String> itemList = new ArrayList<>();
		
		for (int i = 0; i < outfit.getItems().size(); i++) {
		    itemList.add(i, outfit.getItems().get(i).getItemName());
		}
		
		return itemList;
	}
	
}
