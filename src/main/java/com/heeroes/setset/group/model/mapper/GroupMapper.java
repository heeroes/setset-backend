package com.heeroes.setset.group.model.mapper;

import com.heeroes.setset.group.dto.Group;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupMapper {
    int insert(Group group);

    void modify(Group group);
}
