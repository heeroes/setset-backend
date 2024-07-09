package com.heeroes.setset.city.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.heeroes.setset.city.dto.City;

@Mapper
public interface CityMapper {
	List<City> searchBySido(Map<String, Object> param);
	List<City> searchBySidoGu(Map<String, Object> param);
	List<City> getSidoList();
}
