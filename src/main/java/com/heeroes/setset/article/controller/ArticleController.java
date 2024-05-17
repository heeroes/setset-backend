package com.heeroes.setset.article.controller;

import com.heeroes.setset.article.dto.Article;
import com.heeroes.setset.article.model.service.ArticleService;
import com.heeroes.setset.common.Response;
import com.heeroes.setset.user.utils.JwtTokenProvider;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * @param groupId
     * @param attachedFile : 첨부파일
     * @param article
     * @param tokenHeader
     * @return
     * @throws IOException
     */
    @PostMapping("/{groupId}/article")
    public ResponseEntity<Response<String>> create(@PathVariable int groupId,
                                                   @RequestPart List<MultipartFile> attachedFile,
                                                   @RequestPart Article article,
                                                   @RequestHeader("Authorization") String tokenHeader)
            throws IOException {
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        article.setGroupId(groupId);
        article.setUserId(userId);
        log.debug("conroller 진입");
        articleService.create(article, attachedFile);
        return ResponseEntity.ok(Response.success("등록 성공!"));
    }

    /**
     * 게시글 삭제
     *
     * @param groupId
     * @param id          : 게시글 id
     * @param tokenHeader
     * @return
     */
    @DeleteMapping("/{groupId}/article/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable int groupId, @PathVariable int id,
                                                   @RequestHeader("Authorization") String tokenHeader) {
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        articleService.delete(groupId, id, userId);
        return ResponseEntity.ok(Response.success("삭제 성공"));
    }

    /**
     * 게시글 수정
     *
     * @param groupId
     * @param id          : 게시글 id
     * @param article
     * @param tokenHeader
     * @return
     */
    @PutMapping("/{groupId}/article/{id}")
    public ResponseEntity<Response<String>> modify(@PathVariable int groupId, @PathVariable int id,
                                                   @RequestPart List<MultipartFile> newAttachedFile,
                                                   @RequestPart Article article, @RequestPart List<String> deletedFile,
                                                   @RequestHeader("Authorization") String tokenHeader)
            throws IOException {
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        article.setId(id);
        article.setUserId(userId);
        article.setGroupId(groupId);
        articleService.modify(article, newAttachedFile, deletedFile);
        return ResponseEntity.ok(Response.success("수정완료"));
    }

    /**
     * 그룹 게시판 피드(리스트) 조회 - 가장 최근 작성한 글부터 조회
     *
     * @param groupId
     * @param tokenHeader
     * @return
     */
    @GetMapping("/{groupId}/article")
    public ResponseEntity<Response<List<Article>>> getFeed(@PathVariable int groupId,
                                                           @RequestHeader("Authorization") String tokenHeader) {
        int userId = jwtTokenProvider.extractUserId(tokenHeader.substring(7));
        List<Article> articles = articleService.getFeed(groupId, userId);
        return ResponseEntity.ok(Response.success(articles));
    }
}
