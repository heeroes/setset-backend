package com.heeroes.setset.comment.model.mapper;

import com.heeroes.setset.comment.dto.Comment;
import com.heeroes.setset.comment.dto.CommentResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    void create(Comment comment);
    void update(Comment comment);

    Comment findById(int id);

    void deleteById(int id);

    void deleteAllByArticleId(int id);

    List<CommentResponse> getAllByArticleId(int articleId);
}
