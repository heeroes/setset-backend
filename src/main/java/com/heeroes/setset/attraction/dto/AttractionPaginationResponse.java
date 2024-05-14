package com.heeroes.setset.attraction.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class AttractionPaginationResponse {
	private Map<Integer, List<Attraction>> attractions;
	private int totalPages;
	private int page;
	private int size;
}
