package com.heeroes.setset.safe.model.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.heeroes.setset.safe.dto.Hospital;

public interface HospitalDao {
	List<Hospital> searchSafeMapByLocation(Map<String, Object> param) throws IOException, SAXException, ParserConfigurationException;
	List<Hospital> searchSafeMapByKeyword(Map<String, Object> param) throws IOException, SAXException, ParserConfigurationException;
}
