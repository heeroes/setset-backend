package com.heeroes.setset.attraction.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.heeroes.setset.attraction.dto.Attraction;
import com.heeroes.setset.attraction.dto.AttractionPaginationResponse;

public interface AttractionService {
	AttractionPaginationResponse searchByKeyword(int size, int page, int contentTypeId, String keyword);
	Attraction searchById(int id);
	List<Attraction> getPopularityRank();
}
