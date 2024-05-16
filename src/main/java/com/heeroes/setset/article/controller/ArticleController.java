package com.heeroes.setset.article.controller;

import com.heeroes.setset.article.dto.Article;
import com.heeroes.setset.article.model.service.ArticleService;
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
@RequestMapping("/group")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/{groupId}/article")
    public ResponseEntity<Response<String>> create(@PathVariable int  groupId, @RequestBody Article article, @RequestHeader("Authorization") String tokenHeader){
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        article.setGroupId(groupId);
        article.setUserId(userId);
        articleService.create(article);
        return ResponseEntity.ok(Response.success("등록 성공!"));
    }

    @DeleteMapping("/{groupId}/article/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable int groupId, @PathVariable int id,  @RequestHeader("Authorization") String tokenHeader){
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        articleService.delete(groupId,id,userId);
        return ResponseEntity.ok(Response.success("삭제 성공"));
    }

    @PutMapping("/{groupId}/article/{id}")
    public ResponseEntity<Response<String>> modify(@PathVariable int groupId, @PathVariable int id,@RequestBody Article article,  @RequestHeader("Authorization") String tokenHeader){
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        article.setId(id);
        article.setUserId(userId);
        article.setGroupId(groupId);
        articleService.modify(article);
        return ResponseEntity.ok(Response.success("수정완료"));
    }
}
