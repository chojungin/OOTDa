package com.codi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public List<Item> getItems(@RequestHeader String temp) {
		List<Item> itemList = itemService.getItems(temp);
		return itemList;
	}
	
}
