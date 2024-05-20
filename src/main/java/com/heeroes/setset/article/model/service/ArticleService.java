package com.heeroes.setset.article.model.service;

import com.heeroes.setset.article.dto.Article;
import com.heeroes.setset.article.dto.FeedResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    void create(Article article, List<MultipartFile> attachedFiles) throws IOException;

    void delete(int groupId, int id, int userId);

    void modify(Article article, List<MultipartFile> newAttachedFile, List<String> deletedFile) throws IOException;

    List<FeedResponse> getFeed(int groupId, int userId);

    void deleteAllByGroupId(int groupId);
}
