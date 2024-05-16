package com.heeroes.setset.attachedfile.model.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AttachedFileService {
    void uploadFiles(int id, List<MultipartFile> attachedFiles) throws IOException;
}
