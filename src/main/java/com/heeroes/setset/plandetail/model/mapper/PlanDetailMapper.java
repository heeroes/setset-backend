package com.heeroes.setset.plandetail.model.mapper;

import java.util.Map;

import com.heeroes.setset.plandetail.dto.PlanDetail;

public interface PlanDetailMapper {
	/**
	 * 여행 계획에 여행지 추가
	 * @param pd
	 * @return
	 */
	int insertPlanDetail(PlanDetail pd);
	
	/**
	 * 여행 계획에 포함된 특정 여행지 정보 수정
	 * @param param
	 * @return
	 */
	int updatePlanDetail(Map<String, Object> param);
	
	/**
	 * 여행 계획에서 여행지 삭제
	 * @param id
	 * @return
	 */
	int deletePlanDetail(int id);
	
	/**
	 * 특정 여행 계획에 포함된 모든 여행지 정보 수정
	 * @param param
	 * @return
	 */
	int updatePlanDetailList(Map<String, Object> param);
	
	/**
	 * 여행 계획에 여행지 추가 또는 제거 시, popularity 업데이트
	 * @param param
	 * @return
	 */
	int updatePopularity(Map<String, Object> param);
	
	/**
	 * planDetail id 이용 user id 얻어오기
	 * @param id
	 * @return
	 */
	int selectuUserIdByPlanDetil(int id);
}
