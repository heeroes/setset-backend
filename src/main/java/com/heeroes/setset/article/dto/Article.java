package com.heeroes.setset.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private int id;
    private String content;
    private String createdAt;
    private int groupId;
    private int userId;
}
