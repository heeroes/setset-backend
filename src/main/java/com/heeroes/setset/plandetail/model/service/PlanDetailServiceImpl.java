package com.heeroes.setset.plandetail.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.heeroes.setset.attraction.model.mapper.AttractionMapper;
import com.heeroes.setset.plan.model.mapper.PlanMapper;
import com.heeroes.setset.plan.model.service.PlanService;
import com.heeroes.setset.plandetail.dto.PlanDetail;
import com.heeroes.setset.plandetail.model.mapper.PlanDetailMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanDetailServiceImpl implements PlanDetailService {
	private final PlanDetailMapper planDetailMapper;
	private final PlanService planService;
	
	public boolean checkUserMatch(int planDetailId, int userId) {
		int owner = planDetailMapper.selectuUserIdByPlanDetil(planDetailId);
		System.out.println(owner + " : " + userId);
		
		if(owner != userId)
			throw new RuntimeException("권한이 없습니다.");
		return true;
	}
	
	@Override
	public int insertPlanDetail(PlanDetail pd, int userId) {
		planService.checkUserMatch(pd.getPlanId(), userId);
		
		updatePopularity(pd.getAttractionId(), true);
		return planDetailMapper.insertPlanDetail(pd);
	}
	
	@Override
	public int updatePopularity(int id, boolean isInsert) {
		Map<String, Object> param = new HashMap<>();
		
		param.put("id", id);
		param.put("isInsert", isInsert);
		
		return planDetailMapper.updatePopularity(param);
	}
	
	@Override
	public int updatePlanDetail(int id, PlanDetail pd, int userId) {
		checkUserMatch(id, userId);
		
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("planDetail", pd);
		return planDetailMapper.updatePlanDetail(param);
	}

	@Override
	public int updatePlanDetailList(int planId, Map<String, List<PlanDetail>> pdList, int userId) { // day: { [ {}, {}, ...] }
		planService.checkUserMatch(planId, userId);
		
		Map<String, Object> param = new HashMap();
		List<PlanDetail> totalList = new ArrayList();
		for (Map.Entry<String, List<PlanDetail>> entry : pdList.entrySet()) {
			List<PlanDetail> list = entry.getValue();
			int day = Integer.parseInt(entry.getKey());
			for (int i=0;i<list.size();i++) {
				PlanDetail pd = list.get(i);
				pd.setDay(day);
				pd.setOrder(i+1);
				
				totalList.add(pd);
			}
		}
		
		param.put("planId", totalList.get(0).getPlanId());
		param.put("pdList", totalList);
		return planDetailMapper.updatePlanDetailList(param);
	}

	@Override
	public int deletePlanDetail(int id, int userId) {
		// TODO Auto-generated method stub
		checkUserMatch(id, userId);
		updatePopularity(id, false);
		return planDetailMapper.deletePlanDetail(id);
	}

}
