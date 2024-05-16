package com.heeroes.setset.plan.dto;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.attraction.dto.Attraction;

import lombok.Data;

@Data
public class PlanPaginationResponse {
	private List<Plan> plans;
	private int totalPages;
	private int page;
	private int size;
}
