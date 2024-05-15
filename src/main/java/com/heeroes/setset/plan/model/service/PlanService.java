package com.heeroes.setset.plan.model.service;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.plan.dto.Plan;
import com.heeroes.setset.plan.dto.PlanPaginationResponse;

public interface PlanService {
	/**
	 * 여행 계획 생성
	 * @param param (userId, Plan)
	 * @return
	 */
	int createPlan(Map<String, Object> param);
	
	/**
	 * 여행 계획 수정
	 * @param plan
	 * @return
	 */
	int updatePlan(Map<String, Object> param);
	
	/**
	 * 여행 계획 삭제
	 * @param id (plan_id)
	 * @return
	 */
	int deletePlan(int id);
	
	/**
	 * 여행 계획 전체 조회
	 * @param userId
	 * @return
	 */
	PlanPaginationResponse selectAll(int userId);
	
	/**
	 * 여행 계획 상세 조회
	 * @param param (userId, Plan id)
	 * @return
	 */
	Plan selectById(Map<String, Object> param);
	
	/**
	 * 계획 총 개수
	 * @param param
	 * @return
	 */
	int totalRow(Map<String, Object> param);
}
