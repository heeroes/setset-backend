package com.heeroes.setset.chatgpt.model.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heeroes.setset.chatgpt.config.ChatGPTConfig;
import com.heeroes.setset.chatgpt.dto.ChatGPT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    private final ChatGPTConfig chatGPTConfig;

    public ChatGPTServiceImpl(ChatGPTConfig chatGPTConfig) {
        this.chatGPTConfig = chatGPTConfig;
    }

    @Value("${chatgpt.open-api.model}")
    private String model;

    @Override
    public List<Map<String, Object>> modelList() {
        log.debug("[+] 모델 리스트를 조회합니다.");
        List<Map<String, Object>> resultList = null;

        HttpHeaders headers = chatGPTConfig.httpHeaders();

        ResponseEntity<String> response = chatGPTConfig.restTemplate()
                .exchange(
                        "https://api.openai.com/v1/models",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        String.class);

        try {
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> responseMap = om.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});

            resultList = (List<Map<String, Object>>) responseMap.get("data");
            for (Map<String, Object> object : resultList) {
                log.debug("ID: " + object.get("id"));
                log.debug("Object: " + object.get("object"));
                log.debug("Created: " + object.get("created"));
                log.debug("Owned By: " + object.get("owned_by"));
            }
        } catch (JsonMappingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> isValidModel(String modelName) {
        log.debug("[+] 모델이 유효한지 조회합니다. 모델 : " + modelName);
        List<Map<String, Object>> result;

        HttpHeaders headers = chatGPTConfig.httpHeaders();

        ResponseEntity<String> response = chatGPTConfig.restTemplate()
                .exchange(
                        "https://api.openai.com/v1/models/" + modelName,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        String.class);

        try {
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> responseMap = om.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            result = (List<Map<String, Object>>) responseMap.get("data");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<String> prompt(ChatGPT gpt) {
        log.debug("[+] 프롬프트를 수행합니다.");

        List<Map<String, Object>> result = null;

        HttpHeaders headers = chatGPTConfig.httpHeaders();

        String requestBody;
        ObjectMapper om = new ObjectMapper();
        
        String prompt = "국내 관광지 10개를 자세한 지명으로 추천해줘. \n은 빼고 해줘. 줄바꿈, 띄어쓰기 빼줘.";
        
        gpt = gpt.builder()
                .model(model)
                .prompt(prompt)
                .temperature(0.8f)
                .max_tokens(1000)
                .build();

        try {
            requestBody = om.writeValueAsString(gpt);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = chatGPTConfig.restTemplate()
                .exchange(
                        "https://api.openai.com/v1/completions",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        try {
        	Map<String, Object> responseMap = om.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            result = (List<Map<String, Object>>) responseMap.get("choices");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println((String)result.get(0).get("text"));
        String text = (String)result.get(0).get("text");
        return getText(text);
    }
    
    List<String> getText(String text) {
    	List<String> resultList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+\\.\\s+(.*?)($|\\n)");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            resultList.add(matcher.group(1));
        }

        return resultList;
    }
}
