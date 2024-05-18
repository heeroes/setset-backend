package com.heeroes.setset.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private int id;
    private String email;
    private String nickname;
    private String imageKey;
}
