package com.heeroes.setset.safe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heeroes.setset.common.Response;
import com.heeroes.setset.safe.model.service.SafeMapService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/safe/map")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SafeMapController {
	private final SafeMapService safeMapService;
	
	/**
	 * 키워드 기반 안전 지도 정보 조회
	 * @param keyword
	 * @param agencyType
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<?> searchSafeMapByKeyword(
			@RequestParam(defaultValue = "10") int size,
			@RequestParam("keyword") String keyword,
			@RequestParam(required = false) String agencyType) {
		
		return ResponseEntity.ok
				(Response.success
						(safeMapService.searchSafeMapByKeyword
								(size, keyword, agencyType)));
	}
	
	/**
	 * 여행지 위치 기반 안전 지도 정보 조회
	 * @param planId
	 * @return
	 */
	@GetMapping("{planId}")
	public ResponseEntity<?> searchSafeMapByLocation(@RequestParam(defaultValue = "10") int size,
			@PathVariable("planId") int planId,
			@RequestParam(required = false) String agencyType) {
		
		return ResponseEntity.ok(Response.success
				(safeMapService.searchSafeMapByLocation
						(size, planId, agencyType)));
	}
	
}
