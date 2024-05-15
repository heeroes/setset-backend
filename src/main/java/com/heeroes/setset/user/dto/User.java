package com.heeroes.setset.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User {
    private int id;
    private String email;
    private String nickname;
    private OAuthProvider oAuthProvider;
}
