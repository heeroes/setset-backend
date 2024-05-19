package com.heeroes.setset.comment.model.service;

import com.heeroes.setset.comment.dto.Comment;
import com.heeroes.setset.comment.dto.CommentResponse;
import java.util.List;

public interface CommentService {
    void create(Comment comment, int groupId);
    Comment update(Comment comment);

    void deleteById(int id, int userId);

    List<CommentResponse> getAllByArticleId(int articleId, int userId, int groupId);
}
