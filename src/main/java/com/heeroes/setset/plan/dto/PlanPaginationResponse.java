package com.heeroes.setset.plan.dto;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.attraction.dto.Attraction;

public class PlanPaginationResponse {
	private Map<Integer, List<Plan>> plans;
	private int totalPages;
	private int page;
	private int size;
}
