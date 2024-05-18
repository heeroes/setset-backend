package com.heeroes.setset.safe.model.mapper;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.safe.dto.Police;

public interface PoliceMapper {
	List<Police> searchSafeMapByLocation(Map<String, Object> param);
	List<Police> searchSafeMapByKeyword(Map<String, Object> param);
}
