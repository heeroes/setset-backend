package com.heeroes.setset.user.model.service;

import com.heeroes.setset.user.dto.AuthTokens;
import com.heeroes.setset.user.dto.OAuthInfoResponse;
import com.heeroes.setset.user.dto.OAuthLoginParams;
import com.heeroes.setset.user.dto.User;
import com.heeroes.setset.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserMapper userMapper;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params){
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        int userId = findOrCreateUser(oAuthInfoResponse);
        return new AuthTokens("accessToken : " + userId, "refreshToken", "grantType", 100l);
    }

    private int findOrCreateUser(OAuthInfoResponse oAuthInfoResponse){
        User user = userMapper.findByEmail(oAuthInfoResponse.getEmail());
        if(user == null){
            return newUser(oAuthInfoResponse);
        }
        return user.getId();
    }

    private int newUser(OAuthInfoResponse oAuthInfoResponse){
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getNickName())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();
        return userMapper.insert(user);
    }
}
