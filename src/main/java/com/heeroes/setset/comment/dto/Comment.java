package com.heeroes.setset.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private int id;
    private String content;
    private String createdAt;
    private int articleId;
    private int userId;
}
