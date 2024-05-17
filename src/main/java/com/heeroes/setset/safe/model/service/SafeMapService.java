package com.heeroes.setset.safe.model.service;

import java.util.List;

public interface SafeMapService {
	List<?> searchSafeMapByKeyword(int size, String keyword, String agencyType);
	List<?> searchSafeMapByLocation(int size, int planId, String agencyType);
	
}
