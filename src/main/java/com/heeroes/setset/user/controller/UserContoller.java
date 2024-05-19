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
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/login/naver")
    public ResponseEntity<AuthTokens> loginByNaver(@RequestParam String code, @RequestParam String state) {
        NaverLoginParams params = new NaverLoginParams(code, state);
        AuthTokens login = oAuthLoginService.login(params);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:5173/login/success?accessToken=" + login.getAccessToken()));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }


    @GetMapping("/login/google")
    public ResponseEntity<?> loginByGoogle(@RequestParam String code) {
        GoogleLoginParams params = new GoogleLoginParams(code);
        System.out.println("paramr : " + params);
        AuthTokens login = oAuthLoginService.login(params);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:5173/login/success?accessToken=" + login.getAccessToken()));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/profile")
    public ResponseEntity<Response<User>> modifyProfile(@RequestPart MultipartFile userImage,
                                                        @RequestPart String nickname,
                                                        @RequestHeader("Authorization") String tokenHeader)
            throws IOException {
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        User user = userService.modifyProfile(userId, userImage, nickname);
        return ResponseEntity.ok(Response.success(user));
    }

    @GetMapping("/profile")
    public ResponseEntity<Response<UserInfoResponse>> getUserInfo(@RequestHeader("Authorization") String tokenHeader) {
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        UserInfoResponse response = userService.getUserInfo(userId);
        return ResponseEntity.ok(Response.success(response));
    }

}
