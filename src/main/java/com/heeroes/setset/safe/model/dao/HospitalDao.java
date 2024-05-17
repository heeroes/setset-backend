package com.heeroes.setset.safe.model.dao;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.safe.dto.Hospital;

public interface HospitalDao {
	List<Hospital> searchSafeMapByLocation(Map<String, Object> param);
	List<Hospital> searchSafeMapByKeyword(Map<String, Object> param);
}
