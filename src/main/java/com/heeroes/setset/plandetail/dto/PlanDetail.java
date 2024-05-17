package com.heeroes.setset.plandetail.dto;

import com.heeroes.setset.attraction.dto.Attraction;

import lombok.Data;

@Data
public class PlanDetail {
	int id;
	int planId;
	int attractionId;
	int day;
	int order;
	Attraction attraction;
}
