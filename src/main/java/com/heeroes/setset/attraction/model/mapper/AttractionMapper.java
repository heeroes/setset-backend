package com.heeroes.setset.attraction.model.mapper;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.attraction.dto.Attraction;

public interface AttractionMapper {
	/**
	 * 키워드 기반 여행지 검색
	 * @param param
	 * @return
	 */
	List<Attraction> searchByKeyword(Map<String, Object> param);
	
	/**
	 * 여행지 총 개수
	 * @param param
	 * @return
	 */
	int totalRow(Map<String, Object> param);
	
	/**
	 * 여행지 상세 조회
	 * @param id
	 * @return
	 */
	Attraction searchById(int id);
	
	/**
	 * 인기 여행지 순위
	 * @return
	 */
	List<Attraction> getPopularityRank();
}
