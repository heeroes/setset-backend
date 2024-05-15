package com.heeroes.setset.article.model.service;

import com.heeroes.setset.article.dto.Article;
import com.heeroes.setset.article.model.mapper.ArticleMapper;
import com.heeroes.setset.usergroup.dto.UserGroup;
import com.heeroes.setset.usergroup.model.mapper.UserGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{
    private final ArticleMapper articleMapper;
    private final UserGroupMapper userGroupMapper;
    @Override
    public void create(Article article) {
        if(!userGroupMapper.isExist(new UserGroup(article.getUserId(), article.getGroupId())))
            throw new RuntimeException("해당 그룹원만 게시글을 작성할 수 있습니다!");
        articleMapper.create(article);
        System.out.println("article: " + article);
    }
}
