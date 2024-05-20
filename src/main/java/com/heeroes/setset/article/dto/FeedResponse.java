package com.heeroes.setset.article.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FeedResponse {
    private int id;
    private String content;
    private String createdAt;
    private int groupId;
    private int userId;
    private List<String> images;
    private String nickname;
    private String userImageKey;
}
