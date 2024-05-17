package com.heeroes.setset.user.model.service.oAuth;

import com.heeroes.setset.user.dto.OAuthInfoResponse;
import com.heeroes.setset.user.dto.OAuthLoginParams;
import com.heeroes.setset.user.dto.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params); //Access Token 획득
    OAuthInfoResponse requestOauthInfo(String accessToken); // 획득한 Access Token을 기반으로 프로필 정보 획득(email, nickname)
}
