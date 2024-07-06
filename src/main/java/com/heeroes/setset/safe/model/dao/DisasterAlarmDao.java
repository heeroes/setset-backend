package com.heeroes.setset.safe.model.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.xml.sax.SAXException;

import com.heeroes.setset.safe.dto.DisasterAlarm;

public interface DisasterAlarmDao {
	Map<String, Object> getDisasterInfo(int size, int page) throws IOException, SAXException, ParserConfigurationException, URISyntaxException, ParseException, JSONException;
	Map<String, Object> getDisasterInfoByKeyword(int size, int page, String region) throws IOException, SAXException, ParserConfigurationException, URISyntaxException, ParseException, JSONException;
}
