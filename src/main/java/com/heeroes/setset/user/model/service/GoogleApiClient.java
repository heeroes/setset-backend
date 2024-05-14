package com.heeroes.setset.user.model.service;

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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleApiClient implements OAuthApiClient{
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
        MultiValueMap<String,String> body = params.makeBody();

        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", "http://localhost:80/user/login/google");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        GoogleTokens response = restTemplate.postForObject(url, request, GoogleTokens.class);

        assert response != null;
        return response.getAccessToken();

    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
       httpHeaders.add("Authorization", "Bearer "+accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>(httpHeaders);
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        OAuthInfoResponse response =  restTemplate.getForObject(url, GoogleInfoResponse.class, request);
        System.out.println(request);
        return response;

    }
}
