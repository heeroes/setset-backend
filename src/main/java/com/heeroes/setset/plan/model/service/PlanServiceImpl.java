package com.heeroes.setset.plan.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.heeroes.setset.plan.dto.Plan;
import com.heeroes.setset.plan.dto.PlanPaginationResponse;
import com.heeroes.setset.plan.model.mapper.PlanMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {
	private final PlanMapper planMapper;
	
	@Override
	public int createPlan(Plan plan) {
		// TODO Auto-generated method stub
		return planMapper.createPlan(plan);
	}

	@Override
	public int updatePlan(int id, Plan plan) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("plan", plan);

		return planMapper.updatePlan(param);
	}

	@Override
	public int deletePlan(int id) {
		// TODO Auto-generated method stub
		planMapper.deletePlanDetail(id);
		return planMapper.deletePlan(id);
	}

	@Override
	public PlanPaginationResponse selectAll(int size, int page, int userId) {
		// TODO Auto-generated method stub
		/*쿼리에 넘길 파라미터 정보 생성*/
		Map<String, Object> param = new HashMap<>();
		param.put("size", size);
		param.put("offset", (page-1)*size);
		param.put("userId", userId);
		/*======================*/
		
		
		PlanPaginationResponse resp = new PlanPaginationResponse();
		List<Plan> plans = planMapper.selectAll(param);
		
		resp.setPlans(plans);
		
		//페이지네이션 정보 세팅
		int totalRow = planMapper.totalRow();
				
		int totalPages = ((totalRow-1)/size)+1; 
		resp.setTotalPages(totalPages);
		resp.setSize(size);
		resp.setPage(page);

		return resp;
	}

	@Override
	public Plan selectById(int id) {
		// TODO Auto-generated method stub
		return planMapper.selectById(id);
	}

}
