package com.heeroes.setset.safe.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.heeroes.setset.safe.dto.Hospital;

@Repository
public class HospitalDaoImpl implements HospitalDao {
	@Value("${hospital.servicekey}")
	private String key;
	private String url = "http://apis.data.go.kr/B552657/ErmctInfoInqireService";
	
	@Override
	public List<Hospital> searchSafeMapByLocation(Map<String, Object> param) {
		double medianLat = ((double)param.get("maxLat") + (double)param.get("minLat"))/2;
		double medianLong = ((double)param.get("maxLong") + (double)param.get("minLong"))/2;
		//		https://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytLcinfoInqire?serviceKey=wlJif%2BtRv0WDAf5B1Ck97dCr7gbOdN2sBgrKAnPu01LzzJYHoYiG%2BH3PS1HGf0BlD0KjXIxxceZIsRqMbdv%2B7g%3D%3D&WGS84_LON=126.75419885&WGS84_LAT=36.534547325&pageNo=1&numOfRows=10
		return null;
	}

	@Override
	public List<Hospital> searchSafeMapByKeyword(Map<String, Object> param) {
		String serviceName = "getEgytListInfoInqire";
		StringBuilder sb = new StringBuilder();
		sb.append(url).append("/").append(serviceName).append("?")
		.append("serviceKey=").append(key).append("&")
		.append("Q0=").append(param.get("keyword")).append("&")
		.append("numOfRows=").append(param.get("size"));
		
		
		return null;
	}

}
