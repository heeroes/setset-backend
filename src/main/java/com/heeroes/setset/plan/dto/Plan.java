package com.heeroes.setset.plan.dto;

import java.util.List;

import com.heeroes.setset.plandetail.dto.PlanDetail;

import lombok.Data;

@Data
public class Plan {
	int id;
	String title;
	int userId;
	String region;
	String startDate;
	String endDate;
	List<PlanDetail> planDetailList;
}
