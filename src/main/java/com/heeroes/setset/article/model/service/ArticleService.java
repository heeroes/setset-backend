package com.heeroes.setset.article.model.service;

import com.heeroes.setset.article.dto.Article;

public interface ArticleService {
    void create(Article article);

    void delete(int groupId, int id, int userId);
}
