package com.heeroes.setset.user.model.service;

import com.heeroes.setset.user.dto.AuthTokens;
import com.heeroes.setset.user.dto.OAuthInfoResponse;
import com.heeroes.setset.user.dto.OAuthLoginParams;
import com.heeroes.setset.user.dto.User;
import com.heeroes.setset.user.model.mapper.UserMapper;
import com.heeroes.setset.user.utils.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserMapper userMapper;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final AuthTokensGenerator authTokensGenerator;
    public AuthTokens login(OAuthLoginParams params){
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        int userId = findOrCreateUser(oAuthInfoResponse);
        return authTokensGenerator.generate(userId);
    }

    private int findOrCreateUser(OAuthInfoResponse oAuthInfoResponse){
        System.out.println("닉네임 : " + oAuthInfoResponse.getNickName());

        User user = userMapper.findByEmail(oAuthInfoResponse.getEmail());
        if(user == null){   //회원가입 하지 않은 유저라면
            newUser(oAuthInfoResponse);
            return userMapper.findByEmail(oAuthInfoResponse.getEmail()).getId();
        }
        return user.getId();
    }

    private void newUser(OAuthInfoResponse oAuthInfoResponse){
        System.out.println("닉네임 : " + oAuthInfoResponse.getNickName());
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getNickName())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();
        userMapper.insert(user);
    }
}
