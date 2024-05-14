package com.heeroes.setset.user.model.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.heeroes.setset.user.dto.GoogleInfoResponse;
import com.heeroes.setset.user.dto.GoogleLoginParams;
import com.heeroes.setset.user.dto.GoogleTokens;
import com.heeroes.setset.user.dto.NaverInfoResponse;
import com.heeroes.setset.user.dto.OAuthInfoResponse;
import com.heeroes.setset.user.dto.OAuthLoginParams;
import com.heeroes.setset.user.dto.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleApiClient implements OAuthApiClient {
	private static final String GRANT_TYPE = "authorization_code";
	@Value("${oauth.google.url.auth}")
	private String authUrl;

	@Value("${oauth.google.client-id}")
	private String clientId;

	@Value("${oauth.google.client-secret}")
	private String clientSecret;

	@Value("${oauth.google.url.api}")
	String apiUrl;

	private final RestTemplate restTemplate;

	@Override
	public OAuthProvider oAuthProvider() {
		return OAuthProvider.GOOGLE;
	}

	@Override
	public String requestAccessToken(OAuthLoginParams params) {
		String url = authUrl;

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> body = params.makeBody();

		body.add("grant_type", GRANT_TYPE);
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("redirect_uri", "http://localhost:80/user/login/google");

		System.out.println("body: " + body);

		HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
		GoogleTokens response = restTemplate.postForObject(url, request, GoogleTokens.class);
		System.out.println("googletokens:" + response);
		assert response != null;
		return response.getAccessToken();

	}

	@Override
	public GoogleInfoResponse requestOauthInfo(String accessToken) {
		String url = apiUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        JsonNode jsonres = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class).getBody();
        System.out.println("jsonNode:" + jsonres);
        String id = jsonres.get("id").asText();
        String email = jsonres.get("email").asText();
        String nickname = jsonres.get("name").asText();
        System.out.println("id = " + id);
        System.out.println("email = " + email);
        System.out.println("nickname = " + nickname);
        GoogleInfoResponse response = new GoogleInfoResponse(email, nickname);
        return response;
        
	}
}
