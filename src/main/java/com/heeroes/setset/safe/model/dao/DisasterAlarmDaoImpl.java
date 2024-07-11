package com.heeroes.setset.safe.model.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;

import com.heeroes.setset.safe.dto.DisasterAlarm;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class DisasterAlarmDaoImpl implements DisasterAlarmDao {
	@Value("${disaster.alarm.servicekey}")
	private String disasterKey;
	private String disasterUrl = "https://www.safetydata.go.kr/V2/api/";
	
	@Override
	public Map<String, Object> getDisasterInfo(int size, int page)
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException, ParseException, JSONException {
		StringBuilder urlBuilder = new StringBuilder(disasterUrl);
		urlBuilder.append(URLEncoder.encode("DSSP-IF-00247", "UTF-8"));
		urlBuilder.append("?" + "serviceKey=" + disasterKey );
		int lastPage=1;
		int lastPageSize=1;
		if(page == 0) {
			// total count 받아오기
			JSONObject jsonObject = connectApi(urlBuilder, 1);
	        Long totalCount = (Long)jsonObject.get("totalCount");
	        lastPageSize = (int) (totalCount % size);
	        lastPage = (int) (totalCount / size)+1;
		}
		urlBuilder.append("&" + "numOfRows=" + size);
		
		List<DisasterAlarm> list = new ArrayList<>();
		JSONArray jsonArr = (JSONArray) connectApi(urlBuilder, lastPage-1).get("body");
		saveList(list, jsonArr);
		
		if(lastPageSize != 0) {
			jsonArr = (JSONArray) connectApi(urlBuilder, lastPage).get("body");
			saveList(list, jsonArr);			
		}
		
		Collections.sort(list);
		Map<String, Object> results = new HashMap<>();
		results.put("alarms", list);
		results.put("totalCount", list.size());
		
		return results;
	}
        
     private JSONObject connectApi(StringBuilder urlBuilder, int page) throws IOException, URISyntaxException, ParseException {
    	 StringBuilder newUrl = new StringBuilder();
    	 newUrl.append(urlBuilder).append("&" + "pageNo=" + page);
    	 URI uri = new URI(newUrl.toString());
         URL url = uri.toURL();

         /* API 호출하기 위한 HTTP 커넥션과 리더 생성 */
         HttpURLConnection connection =
                 (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("GET");

         BufferedReader reader = null;

         /* API 호출 */
         connection.connect();
         if (connection.getResponseCode() >= 200) {
             reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         } else {
             System.err.println("Connection failed.");
         }
        

         /* API 응답에서 데이터 추출 */
         StringBuilder sb = new StringBuilder();
         String line;
         
	 
         while ((line = reader.readLine()) != null) {
             sb.append(line);

             //reader.close();
             //connection.disconnect();
         }
         
         JSONParser jsonParser = new JSONParser();
         JSONObject jsonObject = (JSONObject)jsonParser.parse(sb.toString());
         
         return jsonObject;
     }
     
     private void saveList(List<DisasterAlarm> results, JSONArray jsonArr){
    	 for (int i = 0; i < jsonArr.size(); i++) {
 			Map<String, String> map = (HashMap) jsonArr.get(i);
 			results.add(new DisasterAlarm(
 					map.get("CRT_DT"), 
 					map.get("MSG_CN"), 
 					map.get("RCPTN_RGN_NM"),
 					map.get("EMRG_STEP_NM")));
 			
 		}
    	 
     }
     
    @Override
    public Map<String, Object> getDisasterInfoByKeyword(int size, int page, String region) throws IOException,
    		SAXException, ParserConfigurationException, URISyntaxException, ParseException, JSONException {
    	
    	List<DisasterAlarm> disasterList = (List<DisasterAlarm>)getDisasterInfo(size, page).get("alarms");  //alarms
    	 
    	List<DisasterAlarm> filteredList = disasterList.stream()
                 							.filter(disasterAlarm -> {
                 								String[] addr = region.trim().split(" ");
                 								log.debug("addr length : " + addr.length);
                 								switch(addr.length) {
                 								case 1:
                 									return disasterAlarm.getRegion().replaceAll("\\s", "").contains(addr[0].replaceAll("\\s", ""));
                 								
                 								case 2:
                 									return disasterAlarm.getRegion().replaceAll("\\s", "").contains((addr[0] + addr[1]).replaceAll("\\s", "")) ||
                 											disasterAlarm.getRegion().replaceAll("\\s", "").contains(addr[0].replaceAll("\\s", "")+"전체");
                 									
                 								case 3:
                 									return disasterAlarm.getRegion().replaceAll("\\s", "").contains(region.replaceAll("\\s", "")) ||
                 											disasterAlarm.getRegion().replaceAll("\\s", "").contains((addr[0] + addr[1]).replaceAll("\\s", "")) ||
                 											disasterAlarm.getRegion().replaceAll("\\s", "").contains(addr[0].replaceAll("\\s", "")+"전체");
                 								
                 								}
												return false;
                 								
                 							})
                 							.collect(Collectors.toList());
    	 
    	Map<String, Object> results = new HashMap<>();
 		results.put("alarms", filteredList);
 		results.put("totalCount", filteredList.size());
 		
 		return results;
    }
}
