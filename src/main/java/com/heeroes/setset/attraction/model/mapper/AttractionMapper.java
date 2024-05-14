package com.heeroes.setset.attraction.model.mapper;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.attraction.dto.Attraction;

public interface AttractionMapper {
	List<Attraction> searchByKeyword(Map<String, Object> param);
	
	int totalRow(Map<String, Object> param);
	
	Attraction searchById(int id);
	
	List<Attraction> getPopularityRank();
}
