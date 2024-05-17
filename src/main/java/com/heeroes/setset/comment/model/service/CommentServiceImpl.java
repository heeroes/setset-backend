package com.heeroes.setset.comment.model.service;

import com.heeroes.setset.comment.dto.Comment;
import com.heeroes.setset.comment.model.mapper.CommentMapper;
import com.heeroes.setset.usergroup.dto.UserGroup;
import com.heeroes.setset.usergroup.model.mapper.UserGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentMapper commentMapper;
    private final UserGroupMapper userGroupMapper;
    @Override
    public void create(Comment comment, int groupId) {
        if(!userGroupMapper.isExist(new UserGroup(comment.getUserId(), groupId)))
            throw new RuntimeException("해당 그룹원만 댓글을 작성할 수 있습니다!");
        commentMapper.create(comment);
    }
}
