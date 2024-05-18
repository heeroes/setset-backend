package com.heeroes.setset.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private int id;
    private String content;
    private String createdAt;
    private String userNickname;
    private String userImageKey;
}
