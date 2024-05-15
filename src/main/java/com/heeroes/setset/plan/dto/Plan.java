package com.heeroes.setset.plan.dto;

import java.util.List;

import lombok.Data;

@Data
public class Plan {
	int id;
	String title;
	int user_id;
	String region;
	String start_date;
	String end_date;
	List<PlanDetail> planDetailList;
}
