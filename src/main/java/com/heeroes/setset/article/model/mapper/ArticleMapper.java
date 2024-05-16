package com.heeroes.setset.article.model.mapper;

import com.heeroes.setset.article.dto.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    void create(Article article);
    Article findById(int id);
    void deleteById(int id);

    void modify(Article article);
}
