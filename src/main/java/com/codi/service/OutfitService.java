package com.codi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codi.entity.Outfit;
import com.codi.repository.ItemRepository;
import com.codi.repository.OutfitRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OutfitService {
	
	private final OutfitRepository outfitRepository;
	private final ItemRepository itemRepository;
	
	public Outfit getOutfitInfo(String temp) {
		
		int val = Integer.parseInt(temp);
		String outfitCode = "";
		
		if (val >= 28) {
		    outfitCode = "B";
		} else if (val >= 23 && val <= 27) {
			outfitCode = "H";
		} else if (val >= 20 && val <= 22) {
			outfitCode = "W";
		} else if (val >= 17 && val <= 19) {
			outfitCode = "N";
		} else if (val >= 12 && val <= 16) {
			outfitCode = "C1";
		} else if (val >= 9 && val <= 11) {
			outfitCode = "C2";
		} else if (val >= 5 && val <= 8) {
			outfitCode = "C3";
		} else if (val <= 4) {
			outfitCode = "F";
		}
		
		Outfit outfit = outfitRepository.findIdByOutfitCode(outfitCode);
		
		return outfit;
	}
	
}
