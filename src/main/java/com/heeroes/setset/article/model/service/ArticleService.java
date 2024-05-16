package com.heeroes.setset.article.model.service;

import com.heeroes.setset.article.dto.Article;
import java.util.List;

public interface ArticleService {
    void create(Article article);

    void delete(int groupId, int id, int userId);

    void modify(Article article);

    List<Article> getFeed(int groupId, int userId);
}
