package com.heeroes.setset.plandetail.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.heeroes.setset.attraction.model.mapper.AttractionMapper;
import com.heeroes.setset.plandetail.dto.PlanDetail;
import com.heeroes.setset.plandetail.model.mapper.PlanDetailMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanDetailServiceImpl implements PlanDetailService {
	private final PlanDetailMapper planDetailMapper;
	
	@Override
	public int insertPlanDetail(PlanDetail pd) {
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
	public int updatePlanDetail(int id, PlanDetail pd) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("planDetail", pd);
		return planDetailMapper.updatePlanDetail(param);
	}

	@Override
	public int updatePlanDetailList(Map<String, List<PlanDetail>> pdList) { // day: { [ {}, {}, ...] }
		
		System.out.println(pdList);
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
	public int deletePlanDetail(int id) {
		// TODO Auto-generated method stub
		updatePopularity(id, false);
		return planDetailMapper.deletePlanDetail(id);
	}

}
