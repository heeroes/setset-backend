package com.heeroes.setset.user.dto;

import com.heeroes.setset.user.dto.oauth.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private int id;
    private String email;
    private String nickname;
    private OAuthProvider oAuthProvider;
    private String originalName;
    private String imageKey;
}
