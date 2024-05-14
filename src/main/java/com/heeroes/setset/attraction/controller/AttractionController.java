package com.heeroes.setset.attraction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heeroes.setset.attraction.dto.Attraction;
import com.heeroes.setset.attraction.dto.AttractionPaginationResponse;
import com.heeroes.setset.attraction.model.service.AttractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("attraction")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttractionController {
	private final AttractionService attractionService;
	
	@GetMapping
	public ResponseEntity<?> searchByKeyword(
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "0") int contentTypeId,
			@RequestParam(required = true) String keyword
	){
		System.out.println("send");
		AttractionPaginationResponse response = attractionService.searchByKeyword(size, page, contentTypeId,keyword);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> searchById(@PathVariable("id")int id){
		Attraction attraction = attractionService.searchById(id);
		
		if(attraction == null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(attraction);
	}
	
	@GetMapping("/rank")
	public ResponseEntity<?> getPopularityRank(){
		List<Attraction> attrationList = attractionService.getPopularityRank();
		return ResponseEntity.ok(attrationList);
	}
}
