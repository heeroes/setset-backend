package com.heeroes.setset.attachedfile.model.mapper;

import com.heeroes.setset.attachedfile.dto.AttachedFile;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachedFileMapper {
    int insertAttachedFileList(List<AttachedFile> attachedFileList);
}
