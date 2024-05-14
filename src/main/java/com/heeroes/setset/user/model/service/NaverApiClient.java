package com.heeroes.setset.user.model.service;

import com.heeroes.setset.user.dto.NaverInfoResponse;
import com.heeroes.setset.user.dto.NaverTokens;
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
public class NaverApiClient implements OAuthApiClient{
    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.naver.url.auth}")
    private String authUrl;

    @Value("${oauth.naver.client-id}")
    private String clientId;

    @Value("${oauth.naver.client-secret}")
    private String clientSecret;

    @Value("${oauth.naver.url.api}")
    String apiUrl;

    private final RestTemplate restTemplate;


    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }


    /*
    네이버 로그인이 완료 후 콜백 URL로 받은 정보를 이용해 accessToken 발급
     */
    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        String url = authUrl + "/oauth2.0/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        NaverTokens response = restTemplate.postForObject(url, request, NaverTokens.class);

        assert response != null;
        return response.getAccessToken();
    }

    /*
    accessToken을 이용해 네이버 프로필 정보를 받아옴
     */
    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/v1/nid/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, NaverInfoResponse.class);
    }
}
