package com.heeroes.setset.user.model.service.oAuth;

import com.heeroes.setset.user.dto.oauth.AuthTokens;
import com.heeroes.setset.user.dto.oauth.OAuthInfoResponse;
import com.heeroes.setset.user.dto.oauth.OAuthLoginParams;
import com.heeroes.setset.user.dto.User;
import com.heeroes.setset.user.model.mapper.UserMapper;
import com.heeroes.setset.user.utils.AuthTokensGenerator;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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
        AuthTokens authTokens = authTokensGenerator.generate(userId);
        saveRefreshToken(authTokens.getRefreshToken(),userId);
        return authTokens;
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
                .nickname(oAuthInfoResponse.getNickName())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();
        userMapper.insert(user);
    }
    
    private void saveRefreshToken(String refreshToken, int userId) {
    	Map<String, Object> param = new HashMap<>();
    	param.put("refreshToken", refreshToken);
    	param.put("userId", userId);
    	int result = userMapper.saveRefreshToken(param);
    	System.out.println("save refreshToken:" + result);
    }
}
