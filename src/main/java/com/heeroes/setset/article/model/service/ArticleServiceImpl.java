package com.heeroes.setset.article.model.service;

import com.heeroes.setset.article.dto.Article;
import com.heeroes.setset.article.model.mapper.ArticleMapper;
import com.heeroes.setset.attachedfile.model.service.AttachedFileService;
import com.heeroes.setset.usergroup.dto.UserGroup;
import com.heeroes.setset.usergroup.model.mapper.UserGroupMapper;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{
    private final ArticleMapper articleMapper;
    private final UserGroupMapper userGroupMapper;
    private final AttachedFileService attachedFileService;
    @Override
    @Transactional
    public void create(Article article, List<MultipartFile> attachedFiles) throws IOException {
        if(!userGroupMapper.isExist(new UserGroup(article.getUserId(), article.getGroupId())))
            throw new RuntimeException("해당 그룹원만 게시글을 작성할 수 있습니다!");
        articleMapper.create(article);
        attachedFileService.uploadFiles(article.getId(), attachedFiles);
        System.out.println("article: " + article);
    }

    @Override
    @Transactional
    public void delete(int groupId, int id, int userId) {
        //게시글을 작성한 사람이 아니면 삭제 불가
        Article article = articleMapper.findById(id);
        if(article.getUserId() != userId)
            throw new RuntimeException("해당 게시글 작성자만 삭제할 수 있습니다!");
        //해당 게시글의 첨부파일도 삭제
        attachedFileService.deleteAllFileByArticleId(id);
        articleMapper.deleteById(id);
    }

    @Override
    public void modify(Article article, List<MultipartFile> newAttachedFile, List<String> deletedFile)
            throws IOException {
        // 게시글 작성한 사람만 수정 가능
        Article finded = articleMapper.findById(article.getId());
        if(finded.getUserId() != article.getUserId())
            throw new RuntimeException("해당 게시글 작성자만 수정할 수 있습니다.");
        // deletedFile 삭제 (db + S3)
        attachedFileService.deleteFileByImageKey(deletedFile);
        // newAttachedFile 추가
        attachedFileService.uploadFiles(article.getId(), newAttachedFile);
        articleMapper.modify(article);
    }

    @Override
    public List<Article> getFeed(int groupId, int userId) {
        // 해당 그룹원만 조회 가능
        if(!userGroupMapper.isExist(new UserGroup(userId, groupId)))
            throw new RuntimeException("해당 그룹원만 피드를 조회할 수 있습니다!");
        return articleMapper.getFeed(groupId);

    }
}
