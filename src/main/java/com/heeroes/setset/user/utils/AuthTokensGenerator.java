package com.heeroes.setset.user.utils;

import com.heeroes.setset.user.dto.AuthTokens;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000*60*60; //60분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000*60*60*24*7; //7일

    private final JwtTokenProvider jwtTokenProvider;

    public AuthTokens generate(int userId){
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String accessToken = jwtTokenProvider.generate(userId,"access-token", accessTokenExpiredAt);
        String refreshToken = jwtTokenProvider.generate(userId, "refresh-token", refreshTokenExpiredAt);

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);

    }


}
