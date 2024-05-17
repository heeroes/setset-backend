package com.heeroes.setset.safe.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.heeroes.setset.attraction.dto.Attraction;
import com.heeroes.setset.attraction.model.service.AttractionService;
import com.heeroes.setset.plan.dto.Plan;
import com.heeroes.setset.plan.model.service.PlanService;
import com.heeroes.setset.plandetail.dto.PlanDetail;
import com.heeroes.setset.safe.model.mapper.GuardHouseMapper;
import com.heeroes.setset.safe.model.mapper.PoliceMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SafeMapServiceImpl implements SafeMapService {
	private final GuardHouseMapper guardHouseMapper;
	private final PoliceMapper policeMapper;
	private final PlanService planService;
	private final AttractionService attractionService;
	
	@Override
	public List<?> searchSafeMapByKeyword(int size, String keyword, String agencyType) {
		Map<String, Object> param = new HashMap();
		param.put("size", size);
		param.put("keyword", keyword);
		
		switch (agencyType) {
			case "police":
				return policeMapper.searchSafeMapByKeyword(param);
			case "guardHouse":
				return guardHouseMapper.searchSafeMapByKeyword(param);
			case "hospital":
				return null;
			default:
				throw new RuntimeException("[police, guardHouse, hospital]에서만 선택 가능");
		}
	}

	@Override
	public List<?> searchSafeMapByLocation(int size, int planId, String agencyType) {
		Map<String,Object> param = calculateLocationRange(planId);
		param.put("size", size);
		switch (agencyType) {
			case "police":
				return policeMapper.searchSafeMapByLocation(param);
			case "guardHouse":
				return guardHouseMapper.searchSafeMapByLocation(param);
			case "hospital":
				return null;
			default:
				throw new RuntimeException("[police, guardHouse, hospital]에서만 선택 가능");
		}
	}
	
	private Map<String, Object> calculateLocationRange(int planId) {
		final double latitudeRange = 0.135;
		final double longitudeRange = 0.169;
		
		Plan plan = planService.selectById(planId);
		double maxLat = Double.MIN_VALUE;
		double minLat = Double.MAX_VALUE;
		double maxLong = Double.MIN_VALUE;
		double minLong = Double.MAX_VALUE;
		
		List<PlanDetail> planDetails = plan.getPlanDetailList();
		for (PlanDetail pd : planDetails) {
			Attraction att = pd.getAttraction();
			double lat = att.getLatitude();
			double Long = att.getLongitude();
			
			maxLat = Math.max(maxLat, lat);
			minLat = Math.min(minLat, lat);
			maxLong = Math.max(maxLong, Long);
			minLong = Math.min(minLong, Long);
		}
		
		maxLat += latitudeRange;
		minLat -= latitudeRange;
		maxLong += longitudeRange;
		minLong -= longitudeRange;
		
		Map<String, Object> location = new HashMap();
		location.put("maxLat", maxLat);
		location.put("minLat", minLat);
		location.put("maxLong", maxLong);
		location.put("minLong", minLong);
		System.out.println(location);
		return location;
	}

}
