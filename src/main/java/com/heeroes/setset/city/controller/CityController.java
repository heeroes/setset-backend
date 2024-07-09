package com.heeroes.setset.city.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heeroes.setset.city.dto.City;
import com.heeroes.setset.city.model.service.CityService;
import com.heeroes.setset.common.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {
		
	private final CityService cityService;
	
	@GetMapping("/search")
	public ResponseEntity<?> searchCity(
			@RequestParam(required = true) String sido,
			@RequestParam(required = false) String gu,
			@RequestParam(required = false) String dong)
	{
		
		List<City> cities = cityService.searchCity(sido, gu, dong);
		return ResponseEntity.ok(Response.success(cities));
	}
}
