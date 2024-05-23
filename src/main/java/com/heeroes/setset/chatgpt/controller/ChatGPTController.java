package com.heeroes.setset.chatgpt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heeroes.setset.chatgpt.dto.ChatGPT;
import com.heeroes.setset.chatgpt.model.service.ChatGPTService;
import com.heeroes.setset.common.Response;

/**
 * ChatGPT API
 *
 */
@RestController
@RequestMapping(value = "/chatGPT")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    /**
     * [API] ChatGPT 모델 리스트를 조회합니다.
     */
    @GetMapping("/modelList")
    public ResponseEntity<?> selectModelList() {
        List<Map<String, Object>> result = chatGPTService.modelList();
        return ResponseEntity.ok(Response.success(result));
    }

    /**
     * [API] ChatGPT 유효한 모델인지 조회합니다.
     *
     * @param modelName
     * @return
     */
    @GetMapping("/model")
    public ResponseEntity<?> isValidModel(@RequestParam(name = "modelName") String modelName) {
    	List<Map<String, Object>> result = chatGPTService.isValidModel(modelName);
        return ResponseEntity.ok(Response.success(result));
    }

    /**
     * 
     */
    @PostMapping("/recommand")
    public ResponseEntity<?> selectPrompt(@RequestBody ChatGPT gpt) {
    	List<String> result = chatGPTService.prompt(gpt);
        return ResponseEntity.ok(Response.success(result));
    }

}