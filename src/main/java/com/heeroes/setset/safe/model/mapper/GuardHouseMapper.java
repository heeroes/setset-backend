package com.heeroes.setset.safe.model.mapper;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.safe.dto.GuardHouse;

public interface GuardHouseMapper {
	List<GuardHouse> searchSafeMapByLocation(Map<String, Object> param);
	List<GuardHouse> searchSafeMapByKeyword(Map<String, Object> param);
}
