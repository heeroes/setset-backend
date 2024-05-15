package com.heeroes.setset.plan.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.heeroes.setset.plan.dto.Plan;
import com.heeroes.setset.plan.dto.PlanPaginationResponse;
import com.heeroes.setset.plan.model.mapper.PlanMapper;

import lombok.Data;

@Data
@Service
public class PlanServiceImpl implements PlanService {
	private final PlanMapper planMapper;
	
	@Override
	public int createPlan(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePlan(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletePlan(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PlanPaginationResponse selectAll(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plan selectById(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int totalRow(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return 0;
	}

}
