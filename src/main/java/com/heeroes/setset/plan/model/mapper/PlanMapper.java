package com.heeroes.setset.plan.model.mapper;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.plan.dto.Plan;

public interface PlanMapper {
	/**
	 * 여행 계획 생성
	 * @param plan
	 * @return
	 */
	int createPlan(Plan plan);
	
	/**
	 * 여행 계획 수정
	 * @param param (id, plan)
	 * @return
	 */
	int updatePlan(Map<String, Object> param);
	
	/**
	 * 여행 계획 삭제
	 * @param id
	 * @return
	 */
	int deletePlan(int id);
	
	int deletePlanDetail(int id);
	
	/**
	 * 여행 계획 전체 조회
	 * @param param (size, offset, userId)
	 * @return
	 */
	List<Plan> selectAll(Map<String, Object> param);
	
	/**
	 * 여행 계획 상세 조회
	 * @param id
	 * @return
	 */
	Plan selectById(int id);
	
	/**
	 * 계획 총 개수
	 * @param userId
	 * @return
	 */
	int totalRow(int userId);
}
