package com.heeroes.setset.plan.model.service;

import com.heeroes.setset.plan.dto.Plan;
import com.heeroes.setset.plan.dto.PlanPaginationResponse;

public interface PlanService {
	/**
	 * 여행 계획 생성
	 * @param Plan
	 * @return
	 */
	int createPlan(Plan plan);
	
	/**
	 * 여행 계획 수정
	 * @param id, plan
	 * @return
	 */
	int updatePlan(int id, Plan plan);
	
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
	PlanPaginationResponse selectAll(int size, int page, int userId);
	
	/**
	 * 여행 계획 상세 조회
	 * @param id (plan_id)
	 * @return
	 */
	Plan selectById(int id);

}
