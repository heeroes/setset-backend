package com.heeroes.setset.plan.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
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

import com.heeroes.setset.common.Response;
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
	@Value("${plan.share.link}")
	private String planShareLink;
	
	@GetMapping("/share/summary")
	public ResponseEntity<?> summaryPlan(
			@RequestParam("id") int id,
			@RequestParam("groupId") int groupId,
			@RequestHeader("Authorization") String tokenHeader) throws JSONException{
		int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
		
		planService.summaryPlan(id, groupId, userId);
		
		return ResponseEntity.ok(Response.success(""));
	}
	
	@GetMapping("/share/{id}")
	public ResponseEntity<?> getShareLink(@PathVariable("id") int id){
		return ResponseEntity.ok(Response.success(planShareLink+'/'+id));
	}
	
	/**
	 * 여행 계획 생성
	 * @param plan
	 * @param tokenHeader
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<?> createPlan(@RequestBody Plan plan, @RequestHeader("Authorization") String tokenHeader){
        int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
        plan.setUserId(userId);
        planService.createPlan(plan);
        
        return ResponseEntity.ok(Response.success(""));
	}
	
	/**
	 * 여행 계획 수정
	 * @param id
	 * @param plan
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePlan(@PathVariable("id") int id, @RequestBody Plan plan){
		planService.updatePlan(id, plan);
		
		return ResponseEntity.ok(Response.success(""));
	}
	
	/**
	 * 여행 계획 삭제
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePlan(@PathVariable("id") int id){
		planService.deletePlan(id);
		return ResponseEntity.ok(Response.success(""));
	}
	
	/**
	 * 특정 사용자의 전체 여행 계획 조회
	 * @param size
	 * @param page
	 * @param tokenHeader
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<?> selectAll(
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "1") int page,
			@RequestHeader("Authorization") String tokenHeader){
		
		int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
		PlanPaginationResponse response= planService.selectAll(size, page, userId);
		
		return ResponseEntity.ok(Response.success(response));
	}
	
	/**
	 * 특정 여행 계획 상세 조회
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> selectById(@PathVariable("id") int id){
		Plan plan = planService.selectById(id);
		return ResponseEntity.ok(Response.success(plan));
	}
	
}
