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
	
	/**
	 * 여행 계획 안에 있는 여행지 모두 삭제
	 * @param id
	 * @return
	 */
	int deletePlanDetail(int id);
	
	/**
	 * 여행 계획 삭제 전 삭제되는 여행지의 popularity 감소
	 * @param id
	 * @return
	 */
	int updatePopularityByPlan(int id);
	
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
	
	/**
	 * 여행 계획 요약 정보 피드 DB 삽입
	 * @param param
	 * @return
	 */
	int insertPlanSummary(Map<String, Object> param);
	
	/**
	 * 여행 계획 소유자의 user_id 조회
	 * @param id
	 * @return
	 */
	int selectUserIdByPlanId(int id);
}
