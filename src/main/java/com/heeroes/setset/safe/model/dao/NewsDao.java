package com.heeroes.setset.safe.model.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import com.heeroes.setset.safe.dto.News;

public interface NewsDao {
	Map<String, Object> getNewsInfo(int size, int page) throws IOException, SAXException, ParserConfigurationException, URISyntaxException, ParseException;
}
