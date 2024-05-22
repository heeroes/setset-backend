package com.heeroes.setset.image.model.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ConnectionOpenAi {
    
    private final String API_KEY = "sk-proj-kF5h26u0AC6PuG47XxU8T3BlbkFJso56zK8dotvKcxRjGfbW";
    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    public void getGeolocation(String address) throws Exception {
    	// Google Custom Search API 호출
        String query = "제주"; // 검색어
        String apiKey = "AIzaSyCsxz30PvC20ZnHGU0cSmQnrKtstqNBlgA"; // Google Custom Search API 키
        String searchEngineId = "948312cf1bd8e46c2"; // Google Custom Search 엔진 ID

        String url = "https://cse.google.com/cse.js?q={query}&key={apiKey}&cx={searchEngineId}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, query, apiKey, searchEngineId);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Search Results:");
            System.out.println(response.getBody());
        } else {
            System.out.println("Failed to retrieve search results. Status code: " + response.getStatusCodeValue());
        }
    }
}
