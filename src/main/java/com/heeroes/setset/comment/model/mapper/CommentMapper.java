package com.heeroes.setset.comment.model.mapper;

import com.heeroes.setset.comment.dto.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    void create(Comment comment);
}
