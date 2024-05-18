package com.heeroes.setset.usergroup.model.mapper;

import com.heeroes.setset.group.dto.GroupResponse;
import com.heeroes.setset.usergroup.dto.UserGroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserGroupMapper {
    int insert(UserGroup userGroup);


    boolean isExist(UserGroup userGroup);

    void delete(UserGroup userGroup);

    int countGroupUser(int groupId);

    List<GroupResponse> findGroupByUserId(int userId);
}
