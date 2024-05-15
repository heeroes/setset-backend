package com.heeroes.setset.plan.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heeroes.setset.plan.model.service.PlanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("plan")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PlanController {
	private final PlanService planService;

	// @GetMapping("search")
	/*
	 * public ResponseEntity<?> searchByKeyword(
	 * 
	 * @RequestParam(defaultValue = "5") int size,
	 * 
	 * @RequestParam(defaultValue = "1") int page,
	 * 
	 * @RequestParam(defaultValue = "0") int contentTypeId,
	 * 
	 * @RequestParam(required = true) String keyword ){ AttractionPaginationResponse
	 * response = attractionService.searchByKeyword(size, page,
	 * contentTypeId,keyword); return ResponseEntity.ok(response);
	 */
	}
	
	/**
	 * 여행지 상세조회
	 * 
	 * @param id : 여행지 id
	 * @return
	 */
//	@GetMapping("search/{id}")
//	public ResponseEntity<?> searchById(@PathVariable("id")int id){
//		Attraction attraction = attractionService.searchById(id);
//		
//		if(attraction == null) return ResponseEntity.notFound().build();
//		
//		return ResponseEntity.ok(attraction);
//	}
	
	
	public ResponseEntity<?> createPlan(@RequestBody Plan groupRequest, @RequestHeader("Authorization") String tokenHeader){
        int userId = tokenProvider.extractUserId(tokenHeader.substring(7));){
	}
	
	public ResponseEntity<?> updatePlan(Map<String, Object> param){
	}
	
	public ResponseEntity<?> deletePlan(int id){
	}

	public ResponseEntity<?> selectAll(int userId){
	}

	public ResponseEntity<?> selectById(Map<String, Object> param){
	}
	
	public ResponseEntity<?> totalRow(Map<String, Object> param){
	}
}
