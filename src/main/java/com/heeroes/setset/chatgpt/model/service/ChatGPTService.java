package com.heeroes.setset.chatgpt.model.service;

import java.util.List;
import java.util.Map;

import com.heeroes.setset.chatgpt.dto.ChatGPT;

public interface ChatGPTService {
	List<Map<String, Object>> modelList();

	List<String> prompt(ChatGPT gpt);

	List<Map<String, Object>> isValidModel(String modelName);
}
