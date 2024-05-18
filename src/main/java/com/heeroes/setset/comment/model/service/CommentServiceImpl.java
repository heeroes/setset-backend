package com.heeroes.setset.comment.model.service;

import com.heeroes.setset.comment.dto.Comment;
import com.heeroes.setset.comment.dto.CommentResponse;
import com.heeroes.setset.comment.model.mapper.CommentMapper;
import com.heeroes.setset.usergroup.dto.UserGroup;
import com.heeroes.setset.usergroup.model.mapper.UserGroupMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentMapper commentMapper;
    private final UserGroupMapper userGroupMapper;
    @Override
    @Transactional
    public void create(Comment comment, int groupId) {
        if(!userGroupMapper.isExist(new UserGroup(comment.getUserId(), groupId)))
            throw new RuntimeException("해당 그룹원만 댓글을 작성할 수 있습니다!");
        commentMapper.create(comment);
    }

    @Override
    @Transactional
    public Comment update(Comment comment) {
        // 댓글 작성한 사람만 수정 가능
        Comment finded = commentMapper.findById(comment.getId());
        if(finded.getUserId() != comment.getUserId())
            throw new RuntimeException("해당 댓글 작성자만 수정할 수 있습니다.");
        commentMapper.update(comment);
        comment.setCreatedAt(finded.getCreatedAt());
        return comment;
    }

    @Override
    @Transactional
    public void deleteById(int id, int userId) {
        // 댓글 작성한 사람만 삭제 가능
        Comment finded = commentMapper.findById(id);
        if(finded.getUserId() != userId)
            throw new RuntimeException("해당 댓글 작성자만 삭제 가능합니다.");
        commentMapper.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getAllByArticleId(int articleId, int userId, int groupId) {
        // 해당 그룹원만 조회 가능
        if(!userGroupMapper.isExist(new UserGroup(userId, groupId)))
            throw new RuntimeException("해당 그룹원만 댓글을 조회할 수 있습니다!");
        return commentMapper.getAllByArticleId(articleId);
    }
}
