package com.heeroes.setset.user.controller;

import com.heeroes.setset.common.Response;
import com.heeroes.setset.user.dto.UserInfoResponse;
import com.heeroes.setset.user.dto.oauth.AuthTokens;
import com.heeroes.setset.user.dto.oauth.GoogleLoginParams;
import com.heeroes.setset.user.dto.oauth.NaverLoginParams;
import com.heeroes.setset.user.dto.User;
import com.heeroes.setset.user.model.service.UserService;
import com.heeroes.setset.user.model.service.oAuth.OAuthLoginService;
import com.heeroes.setset.user.utils.JwtTokenProvider;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserContoller {

    private final OAuthLoginService oAuthLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/login/naver")
    public ResponseEntity<AuthTokens> loginByNaver(@RequestBody NaverLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @GetMapping("/login/google")
    public ResponseEntity<AuthTokens> loginByGoogle(@RequestParam String code) {
        GoogleLoginParams params = new GoogleLoginParams(code);
        System.out.println("paramr : " + params);
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @PostMapping("/profile")
    public ResponseEntity<Response<User>> modifyProfile(@RequestPart MultipartFile userImage, @RequestPart String nickname,
                                                  @RequestHeader("Authorization") String tokenHeader) throws IOException {
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        User user = userService.modifyProfile(userId, userImage, nickname);
        return ResponseEntity.ok(Response.success(user));
    }

    @GetMapping("/profile")
    public ResponseEntity<Response<UserInfoResponse>> getUserInfo(@RequestHeader("Authorization") String tokenHeader){
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        UserInfoResponse response = userService.getUserInfo(userId);
        return ResponseEntity.ok(Response.success(response));
    }

}
