package com.heeroes.setset.user.controller;

import com.heeroes.setset.user.dto.AuthTokens;
import com.heeroes.setset.user.dto.NaverLoginParams;
import com.heeroes.setset.user.model.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserContoller {

    private final OAuthLoginService oAuthLoginService;

    @GetMapping("/login/naver")
    public ResponseEntity<AuthTokens> loginByNaver(@RequestBody NaverLoginParams params){
        return ResponseEntity.ok( oAuthLoginService.login(params));
    }
}
