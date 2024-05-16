package com.heeroes.setset.plandetail.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heeroes.setset.common.Response;
import com.heeroes.setset.plandetail.dto.PlanDetail;
import com.heeroes.setset.plandetail.model.service.PlanDetailService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/plan/detail")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PlanDetailController {
	private final PlanDetailService planDetailService;
	
	/**
	 * 여행 계획에 여행지 추가
	 * @param planDetail
	 * @return
	 */
	@PostMapping("")
	public ResponseEntity<?> insertPlanDetail(@RequestBody PlanDetail planDetail) {
		//TODO: process POST request
		planDetailService.insertPlanDetail(planDetail);
		return ResponseEntity.ok(Response.success(""));
	}
	
	/**
	 * 여행 계획에 포함된 특정 여행지 정보 수정
	 * @param id
	 * @param planDetail
	 * @return
	 */
	@PutMapping("{id}")
	public ResponseEntity<?> updatePlanDetail(@PathVariable("id") int id,
			@RequestBody PlanDetail planDetail) {
		//TODO: process POST request
		planDetailService.updatePlanDetail(id, planDetail);
		return ResponseEntity.ok(Response.success(""));
	}
	
	/**
	 * 특정 여행 계획에 포함된 모든 여행지 정보 수정
	 * @param planDetailList
	 * @return
	 */
	@PutMapping("")
	public ResponseEntity<?> updatePlanDetailList(@RequestBody Map<String,List<PlanDetail>> planDetailList) {
		//TODO: process POST request
		planDetailService.updatePlanDetailList(planDetailList);
		return ResponseEntity.ok(Response.success(""));
	}
	
	/**
	 * 여행 계획에서 여행지 삭제
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletePlanDetail(@PathVariable("id") int id) {
		//TODO: process POST request
		planDetailService.deletePlanDetail(id);
		return ResponseEntity.ok(Response.success(""));
	}
	
}
