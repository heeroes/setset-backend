package com.heeroes.setset.city.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.heeroes.setset.city.dto.City;
import com.heeroes.setset.city.model.mapper.CityMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
	
	private final CityMapper cityMapper;
	
	@Override
	public List<City> searchCity(String sido, String gu) {
		Map<String, Object> param = new HashMap();
		
		if(gu != null && !gu.isBlank()) {
			param.put("sido", sido);
			param.put("gu", gu);
			return cityMapper.searchBySidoGu(param);			
		}
		
		else if(sido != null && !sido.isBlank()) {
			param.put("sido", sido);
			return cityMapper.searchBySido(param);			
		}
		
		return cityMapper.getSidoList();
		
	}

}
