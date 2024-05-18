package com.heeroes.setset.comment.controller;

import com.heeroes.setset.comment.dto.Comment;
import com.heeroes.setset.comment.model.service.CommentService;
import com.heeroes.setset.common.Response;
import com.heeroes.setset.user.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class CommentController {
    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/{groupId}/article/{articleId}/comment")
    public ResponseEntity<Response<String>> create(@PathVariable int groupId, @PathVariable int articleId,
                                                   @RequestBody Comment comment,
                                                   @RequestHeader("Authorization") String tokenHeader) {
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        comment.setArticleId(articleId);
        comment.setUserId(userId);
        commentService.create(comment, groupId);
        return ResponseEntity.ok(Response.success("댓글 등록 완료"));
    }

    @PutMapping("/{groupId}/article/{articleId}/comment/{id}")
    public ResponseEntity<Response<Comment>> update(@PathVariable int groupId, @PathVariable int articleId,
                                                    @PathVariable int id,
                                                    @RequestBody Comment comment,
                                                    @RequestHeader("Authorization") String tokenHeader) {
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        comment.setArticleId(articleId);
        comment.setUserId(userId);
        comment.setId(id);
        Comment updated = commentService.update(comment);
        return ResponseEntity.ok(Response.success(updated));
    }

    @DeleteMapping("/{groupId}/article/{articleId}/comment/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable int groupId, @PathVariable int articleId,
                                                   @PathVariable int id,@RequestHeader("Authorization") String tokenHeader){
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        commentService.deleteById(id, userId);
        return ResponseEntity.ok(Response.success("댓글 삭제 완료"));
    }
}
