package com.heeroes.setset.attachedfile.model.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AttachedFileService {
    void uploadFiles(int articleId, List<MultipartFile> attachedFiles) throws IOException;

    void deleteAllFileByArticleId(int articleId);
    void deleteFileByImageKey(List<String> deletedImageKey);
}
