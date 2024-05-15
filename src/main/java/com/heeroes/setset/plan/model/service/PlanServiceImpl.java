package com.heeroes.setset.plan.model.service;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.heeroes.setset.attraction.model.mapper.AttractionMapper;
import com.heeroes.setset.plan.dto.Plan;
import com.heeroes.setset.plan.dto.PlanPaginationResponse;
import com.heeroes.setset.plan.model.mapper.PlanMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {
	private final PlanMapper planMapper;
	private final AttractionMapper attractionMapper;
	
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
		int totalRow = planMapper.totalRow(userId);
				
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
	
	@Override
	public int summaryPlan(int id, int groupId, int userId) throws JSONException {
		Plan plan = planMapper.selectById(id);
		
		System.out.println("time: " + plan.getStartDate());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    LocalDate startDate = LocalDate.parse(plan.getStartDate(), formatter);
	    LocalDate endDate = LocalDate.parse(plan.getEndDate(), formatter);
		long days = DAYS.between(startDate, endDate) + 1;
		
		JSONObject summary = new JSONObject();
		summary.put("region", plan.getRegion());
		summary.put("start_date", plan.getStartDate());
		if(days < 2)
			summary.put("days", "당일치기");
		else
			summary.put("days", (days-1)+"박 "+(days)+"일");
		
		int size = plan.getPlanDetailList().size();
		summary.put("total_attraction", size);
		
		Set<Integer> contentTypes = new HashSet();
		for (int i = 0; i < size; i++) {
			contentTypes.add(attractionMapper.searchById
					(plan.getPlanDetailList().get(i).getAttractionId())
					.getContentTypeId());	
		}
		
		summary.put("contentTypes", contentTypes);
				
		Map<String, Object> param = new HashMap<>();
		param.put("content", summary.toString());
		param.put("groupId", groupId);
		param.put("userId", userId);
		
		System.out.println("input date : " + param);
		
		return planMapper.insertPlanSummary(param);
	}

}
