package com.heeroes.setset.plandetail.model.service;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.plandetail.dto.PlanDetail;

public interface PlanDetailService {
	/**
	 * 여행 계획에 여행지 추가
	 * @param pd
	 * @return
	 */
	int insertPlanDetail(PlanDetail pd);
	
	/**
	 * 여행 계획에 포함된 특정 여행지 정보 수정
	 * @param id
	 * @param pd
	 * @return
	 */
	int updatePlanDetail(int id, PlanDetail pd);
	
	/**
	 * 특정 여행 계획에 포함된 모든 여행지 정보 수정
	 * @param pdList
	 * @return
	 */
	int updatePlanDetailList(Map<String, List<PlanDetail>> pdList);
	
	/**
	 * 여행 계획에서 여행지 삭제
	 * @param id
	 * @return
	 */
	int deletePlanDetail(int id);
	
	/**
	 * 여행 계획에 여행지 추가 또는 제거 시, popularity 업데이트
	 * @param attractionId
	 * @param isInsert
	 * @return
	 */
	int updatePopularity(int attractionId, boolean isInsert);
}
