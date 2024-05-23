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
import com.heeroes.setset.common.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("attraction")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttractionController {
	private final AttractionService attractionService;
	
	/**
	 * 키워드 기반 여행지 검색
	 *  - 키워드가 포함된 여행지 리스트 반환
	 *  - 여행지 타입이 0인 경우, 여행지 타입에 따른 여행지 리스트 반환
	 * @param size : 한 페이지에서 보여줄 여행지 개수
	 * @param page : 페이지
	 * @param contentTypeId : 여행지 타입
	 * @param keyword : 검색 키워드
	 * @return
	 */
	@GetMapping("/search")
	public ResponseEntity<?> searchByKeyword(
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "0") int contentTypeId,
			@RequestParam(required = true) String keyword
	){
		
		AttractionPaginationResponse response = attractionService.searchByKeyword(size, page, contentTypeId,keyword);
		return ResponseEntity.ok(Response.success(response));
	}
	
	/**
	 * 여행지 상세조회
	 * 
	 * @param id : 여행지 id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> searchById(@PathVariable("id")int id){
		Attraction attraction = attractionService.searchById(id);
		
		if(attraction == null) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(Response.success(attraction));
	}
	
	/**
	 * 인기 여행지 순위
	 * @return
	 */
	@GetMapping("/rank")
	public ResponseEntity<?> getPopularityRank(){
		List<Attraction> attrationList = attractionService.getPopularityRank();
		return ResponseEntity.ok(Response.success(attrationList));
	}
}
