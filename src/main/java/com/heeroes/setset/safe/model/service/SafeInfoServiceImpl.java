package com.heeroes.setset.safe.model.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.heeroes.setset.safe.model.dao.DisasterAlarmDao;
import com.heeroes.setset.safe.model.dao.NewsDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SafeInfoServiceImpl implements SafeInfoService {
	private final DisasterAlarmDao disasterAlarmDao;
	private final NewsDao newsDao;
	
	@Override
	public Map<String, Object> getNewsInfo(int size, int page) throws IOException, SAXException, ParserConfigurationException, URISyntaxException, ParseException {
		// TODO Auto-generated method stub
		return newsDao.getNewsInfo(size, page);
	}

	@Override
	public Map<String, Object> getDisasterInfo(int size, int page)
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException, ParseException, JSONException {
		return disasterAlarmDao.getDisasterInfo(size, page);
	}

}
