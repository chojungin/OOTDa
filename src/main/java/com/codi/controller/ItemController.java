package com.codi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codi.entity.Item;
import com.codi.service.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/item")
@RequiredArgsConstructor
@RestController 
public class ItemController {
	
	private final ItemService itemService;
	
	@GetMapping("/get")
	public List<Item> getItems(@RequestParam("temp") String temp) {
		
		log.info("*********getItems*********");
		try {
			List<Item> itemList = itemService.getItems(temp);
			return itemList;
			
		} catch (Exception e) {
    		log.error("getItems Error : " + e);
    		return null;
    	}
	}
	
}
