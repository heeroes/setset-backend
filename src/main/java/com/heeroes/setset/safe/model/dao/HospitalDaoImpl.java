package com.heeroes.setset.safe.model.dao;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.heeroes.setset.safe.dto.Hospital;

@Repository
public class HospitalDaoImpl implements HospitalDao {
	// 응급 의료 센터
	@Value("${emergencyCenter.servicekey}")
	private String emergencyKey;
	private String emergencyUrl = "http://apis.data.go.kr/B552657/ErmctInfoInqireService";
	
	// 병원
	@Value("${hospital.servicekey}")
	private String hospitalKey;
	private String hospitalUrl = "https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList";
	
	// 약국
	@Value("${pharmacy.servicekey}")
	private String pharmacyKey; 
	private String pharmacyUrl = "https://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList";
	
	/**
	 * 위/경도 기반 병원 정보 조회
	 */
	@Override
	public List<Hospital> searchSafeMapByLocation(Map<String, Object> param) throws IOException, SAXException, ParserConfigurationException {
		double medianLat = ((double)param.get("maxLat") + (double)param.get("minLat"))/2;
		double medianLong = ((double)param.get("maxLong") + (double)param.get("minLong"))/2;
		
		param.put("medianLat", medianLat);
		param.put("medianLong", medianLong);
	
		List<Hospital> hospitalList = emergencyCenterByLocation(medianLat, medianLong, (int)param.get("size"));
		hospitalList.addAll(getHospitalResponse("location", param));
		hospitalList.addAll(getPharmacyResponse("location", param));
		
		return hospitalList;
	}

	/**
	 * 키워드 기반 병원 정보 조회
	 */
	@Override
	public List<Hospital> searchSafeMapByKeyword(Map<String, Object> param) throws IOException, SAXException, ParserConfigurationException {
		List<Hospital> hospitalList = emergencyCenterByKeyword(param);
		hospitalList.addAll(getHospitalResponse("keyword", param));
		hospitalList.addAll(getPharmacyResponse("keyword", param));
		
		return hospitalList;
		
	}
	
	private Document getResponse(StringBuilder urlSb) throws IOException, SAXException, ParserConfigurationException {

		URL url = new URL(urlSb.toString());
			
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); //DocumentBuilderFactory 인스턴스를 생성
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); //DocumentBuilder의 인스턴스를 할당
        
        InputStream inputStream = url.openStream(); //연결된 url InputStream으로
        Document doc = dBuilder.parse(inputStream); //XML파일 파싱 
        doc.getDocumentElement().normalize(); //DOM Tree가 XML 문서의 구조 생성
        
        return doc;

	}
	
	public List<Hospital> emergencyCenterByLocation(double medianLat, double medianLong, int size) throws IOException, SAXException, ParserConfigurationException {
		
		String serviceName = "getEgytLcinfoInqire";
		StringBuilder sb = new StringBuilder();
		sb.append(emergencyUrl).append("/").append(serviceName).append("?")
		.append("serviceKey=").append(emergencyKey).append("&")
		.append("WGS84_LON=").append(medianLong).append("&")
		.append("WGS84_LAT=").append(medianLat).append("&")
		.append("numOfRows=").append(size);
		
		Document doc = getResponse(sb);
		
		NodeList idList = doc.getElementsByTagName("hpid"); 
        NodeList nameList = doc.getElementsByTagName("dutyName"); 
        NodeList addrList = doc.getElementsByTagName("dutyAddr"); 
        NodeList LatList = doc.getElementsByTagName("latitude"); 
        NodeList LonList = doc.getElementsByTagName("longitude");
        NodeList telList = doc.getElementsByTagName("dutyTel1"); 
        
        
        List<Hospital> hospitalList = new ArrayList();
        for (int i = 0; i < idList.getLength(); i++) {
        	hospitalList.add(new Hospital(idList.item(i).getTextContent(),
        			nameList.item(i).getTextContent(), 
        			addrList.item(i).getTextContent(), 
        			Double.parseDouble(LatList.item(i).getTextContent()),
        			Double.parseDouble(LonList.item(i).getTextContent()),
        			telList.item(i).getTextContent()));		
		}
		return hospitalList;
	}
	
	public List<Hospital> getHospitalResponse(String type, Map<String, Object> param) throws IOException, SAXException, ParserConfigurationException {
		StringBuilder sb = new StringBuilder();
		sb.append(hospitalUrl).append("?")
		.append("serviceKey=").append(hospitalKey).append("&")
		.append("numOfRows=").append(param.get("size")).append("&");
		
		switch(type) {
		case "keyword":
			sb.append("emdongNm=").append(URLEncoder.encode((String) param.get("keyword"), "utf-8"));
			break;
		case "location":
			sb.append("xPos=").append(param.get("medianLong")).append("&")
			.append("yPos=").append(param.get("medianLat")).append("&")
			.append("radius=").append("10000");			
			
		}
		Document doc = getResponse(sb);
		
//		NodeList idList = doc.getElementsByTagName("postNo"); 
        NodeList nameList = doc.getElementsByTagName("yadmNm"); 
        NodeList addrList = doc.getElementsByTagName("addr"); 
        NodeList LatList = doc.getElementsByTagName("YPos"); 
        NodeList LonList = doc.getElementsByTagName("XPos");
                
        List<Hospital> hospitalList = new ArrayList();
        for (int i = 0; i < nameList.getLength(); i++) {
//        	NodeList nodes = itemList.item(i).getChildNodes();
        	String id = String.valueOf(i+1);
        	hospitalList.add(new Hospital(id,
        			nameList.item(i).getTextContent(), 
        			addrList.item(i).getTextContent(), 
        			Double.parseDouble(LatList.item(i).getTextContent()),
        			Double.parseDouble(LonList.item(i).getTextContent()),
        			""));		
		}
		return hospitalList;
	}
	
	public List<Hospital> getPharmacyResponse(String type, Map<String, Object> param) throws IOException, SAXException, ParserConfigurationException {
		StringBuilder sb = new StringBuilder();
		sb.append(pharmacyUrl).append("?")
		.append("serviceKey=").append(pharmacyKey).append("&")
		.append("numOfRows=").append(param.get("size")).append("&");
		
		switch(type) {
		case "keyword":
			sb.append("emdongNm=").append(URLEncoder.encode((String) param.get("keyword"), "utf-8"));
			break;
		case "location":
			sb.append("xPos=").append(param.get("medianLong")).append("&")
			.append("yPos=").append(param.get("medianLat")).append("&")
			.append("radius=").append("10000");			
			
		}
		Document doc = getResponse(sb);
		
//		NodeList idList = doc.getElementsByTagName("postNo"); 
        NodeList nameList = doc.getElementsByTagName("yadmNm"); 
        NodeList addrList = doc.getElementsByTagName("addr"); 
        NodeList LatList = doc.getElementsByTagName("YPos"); 
        NodeList LonList = doc.getElementsByTagName("XPos");
        

        List<Hospital> hospitalList = new ArrayList();
        for (int i = 0; i < nameList.getLength(); i++) {
        	String id = String.valueOf(i+1);
        	hospitalList.add(new Hospital(id,
        			nameList.item(i).getTextContent(), 
        			addrList.item(i).getTextContent(), 
        			Double.parseDouble(LatList.item(i).getTextContent()),
        			Double.parseDouble(LonList.item(i).getTextContent()),
        			""));		
		}
		return hospitalList;
	}
	
	
	public List<Hospital> emergencyCenterByKeyword(Map<String, Object> param) throws IOException, SAXException, ParserConfigurationException {
		String serviceName = "getEgytListInfoInqire";
		StringBuilder sb = new StringBuilder();
		sb.append(emergencyUrl).append("/").append(serviceName).append("?")
		.append("serviceKey=").append(emergencyKey).append("&")
		.append("Q0=").append(URLEncoder.encode((String) param.get("keyword"),"utf-8")).append("&")
		.append("Q1=").append(URLEncoder.encode((String) param.get("keyword"),"utf-8")).append("&")
		.append("numOfRows=").append(param.get("size"));
		
		Document doc = getResponse(sb);
		NodeList idList = doc.getElementsByTagName("hpid"); 
        NodeList nameList = doc.getElementsByTagName("dutyName"); 
        NodeList addrList = doc.getElementsByTagName("dutyAddr"); 
        NodeList LatList = doc.getElementsByTagName("wgs84Lat"); 
        NodeList LonList = doc.getElementsByTagName("wgs84Lon");
        NodeList telList = doc.getElementsByTagName("dutyTel1"); 
 
        List<Hospital> hospitalList = new ArrayList();
        for (int i = 0; i < idList.getLength(); i++) {
        	hospitalList.add(new Hospital(idList.item(i).getTextContent(),
        			nameList.item(i).getTextContent(), 
        			addrList.item(i).getTextContent(), 
        			Double.parseDouble(LatList.item(i).getTextContent()),
        			Double.parseDouble(LonList.item(i).getTextContent()),
        			telList.item(i).getTextContent()));		
		}
		return hospitalList;
	}
}
