package com.heeroes.setset.plan.model.service;

import org.springframework.boot.configurationprocessor.json.JSONException;

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
	int updatePlan(int id, Plan plan, int userId);
	
	/**
	 * 여행 계획 삭제
	 * @param id
	 * @return
	 */
	int deletePlan(int id, int userId);
	
	/**
	 * 여행 계획 전체 조회
	 * @param size, page, userId
	 * @return
	 */
	PlanPaginationResponse selectAll(int size, int page, int userId);
	
	/**
	 * 여행 계획 상세 조회
	 * @param id
	 * @return
	 */
	Plan selectById(int id);
	
	/**
	 * 계획 요약 정보 -> 게시판 저장
	 * @param id
	 * @param groupId
	 * @param userId
	 * @return
	 * @throws JSONException
	 */
	int summaryPlan(int id, int groupId, int userId) throws JSONException;
	
	/**
	 * plan id에 해당하는 계획 소유자와 현재 사용자가 일치하는지 확인
	 * @param planId
	 * @param userId
	 * @return
	 */
	boolean checkUserMatch(int planId, int userId);
	
}
