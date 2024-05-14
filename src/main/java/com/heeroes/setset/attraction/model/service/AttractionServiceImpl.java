package com.heeroes.setset.attraction.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.heeroes.setset.attraction.dto.Attraction;
import com.heeroes.setset.attraction.dto.AttractionPaginationResponse;
import com.heeroes.setset.attraction.model.mapper.AttractionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {
	private final AttractionMapper attractionMapper;
	
	@Override
	public AttractionPaginationResponse searchByKeyword(int size, int page, int contentTypeId, String keyword) {
		
		/*쿼리에 넘길 파라미터 정보 생성*/
		Map<String, Object> param = new HashMap<>();
		param.put("size", size);
		param.put("offset", (page-1)*size);
		param.put("contentTypeId", contentTypeId);
		param.put("keyword", keyword);
		/*======================*/
		
		System.out.println("contentType : " + contentTypeId);
		
		
		/*책 목록 조회 응답 데이터 생성*/
		AttractionPaginationResponse resp = new AttractionPaginationResponse();
		//책목록 정보 세팅
		List<Attraction> attractions = attractionMapper.searchByKeyword(param);
		
		Map<Integer, List<Attraction>> map = new HashMap<>();
		if(contentTypeId > 0)
			map.put(contentTypeId, attractions);
		else {
			for (Attraction att : attractions) {
				if(!map.containsKey(att.getContentTypeId()))
					map.put(att.getContentTypeId(), new ArrayList<Attraction>());
				
				map.get(att.getContentTypeId()).add(att);
			}
		}
		resp.setAttractions(map);
		
		//페이지네이션 정보 세팅
		int totalRow = attractionMapper.totalRow(param);
		
		System.out.println("totalRow " + totalRow);
		
		int totalPages = ((totalRow-1)/size)+1; 
		resp.setTotalPages(totalPages);
		resp.setSize(size);
		resp.setPage(page);
		/*======================*/
		
		
		
		return resp;
	}

	@Override
	public Attraction searchById(int id) {
		return attractionMapper.searchById(id);
	}

	@Override
	public List<Attraction> getPopularityRank() {
		return attractionMapper.getPopularityRank();
	}

}
