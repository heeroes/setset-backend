package com.heeroes.setset.plan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heeroes.setset.plan.dto.Plan;
import com.heeroes.setset.plan.dto.PlanPaginationResponse;
import com.heeroes.setset.plan.model.service.PlanService;
import com.heeroes.setset.user.utils.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("plan")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PlanController {
	private final PlanService planService;
	private final JwtTokenProvider tokenProvider;
	
	@PostMapping()
	public ResponseEntity<?> createPlan(@RequestBody Plan plan, @RequestHeader("Authorization") String tokenHeader){
        int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
        plan.setUserId(userId);
        planService.createPlan(plan);
        System.out.println("data : " + plan.getTitle());
        
        return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePlan(@PathVariable("id") int id, @RequestBody Plan plan){
		planService.updatePlan(id, plan);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePlan(@PathVariable("id") int id){
		planService.deletePlan(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping()
	public ResponseEntity<?> selectAll(
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "1") int page,
			@RequestHeader("Authorization") String tokenHeader){
		
		int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
		PlanPaginationResponse response= planService.selectAll(size, page, userId);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> selectById(@PathVariable("id") int id){
		Plan plan = planService.selectById(id);
		return ResponseEntity.ok(plan);
	}
	
}
