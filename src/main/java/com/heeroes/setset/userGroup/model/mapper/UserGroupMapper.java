package com.heeroes.setset.userGroup.model.mapper;

import com.heeroes.setset.userGroup.dto.UserGroup;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserGroupMapper {
    int insert(UserGroup userGroup);
}
