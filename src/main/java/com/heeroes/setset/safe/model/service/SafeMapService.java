package com.heeroes.setset.safe.model.service;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface SafeMapService {
	/**
	 * 키워드 기반 안전 정보 지도 정보 조회
	 * @param size
	 * @param keyword
	 * @param agencyType
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	List<?> searchSafeMapByKeyword(int size, String keyword, String agencyType) throws IOException, SAXException, ParserConfigurationException;
	
	/**
	 * 위/경도 기반 안전 정보 지도 정보 조회
	 * @param size
	 * @param planId
	 * @param agencyType
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	List<?> searchSafeMapByLocation(int size, int planId, String agencyType) throws IOException, SAXException, ParserConfigurationException;
	
}
