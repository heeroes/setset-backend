package com.heeroes.setset.attachedfile.model.service;

import com.heeroes.setset.attachedfile.dto.AttachedFile;
import com.heeroes.setset.attachedfile.model.mapper.AttachedFileMapper;
import com.heeroes.setset.util.S3Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachedFileServiceImpl implements AttachedFileService{
    private final S3Util s3Util;
    private final AttachedFileMapper attachedFileMapper;

    @Override
    @Transactional
    public void uploadFiles(int articleId, List<MultipartFile> attachedFiles) throws IOException {
        //파일 경로
        String filePath = "article/" +articleId + "/";
        List<AttachedFile> uploadFileList = new ArrayList<>();
        for(MultipartFile file : attachedFiles){
            AttachedFile uploadFile = s3Util.upload(file, filePath);
            uploadFile.setArticleId(articleId);
            uploadFileList.add(uploadFile);
            System.out.println("uploadFile:" + uploadFile);
        }
        attachedFileMapper.insertAttachedFileList(uploadFileList);
    }

    @Override
    public void deleteAllFileByArticleId(int articleId) {
        List<AttachedFile> attachedFileList = attachedFileMapper.findByArticleId(articleId);
        System.out.println("attachFileList:" + attachedFileList);
        for(AttachedFile attachedFile : attachedFileList){
            s3Util.deleteImageFromS3(attachedFile.getImageKey());
        }
        attachedFileMapper.deleteAllById(attachedFileList);
    }

    @Override
    public void deleteFileByImageKey(List<String> deletedImageKey) {
        //DB에서 삭제
        attachedFileMapper.deleteFileByImageKey(deletedImageKey);
        //S3에서 삭제
        for(String imageKey : deletedImageKey){
            s3Util.deleteImageFromS3(imageKey);
        }
    }


}
