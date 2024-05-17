package com.heeroes.setset.comment.model.service;

import com.heeroes.setset.comment.dto.Comment;

public interface CommentService {
    void create(Comment comment, int groupId);
}
