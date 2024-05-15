package com.heeroes.setset.group.model.service;

import com.heeroes.setset.group.dto.Group;
import com.heeroes.setset.group.dto.GroupRequest;
import com.heeroes.setset.group.dto.GroupResponse;
import com.heeroes.setset.group.model.mapper.GroupMapper;
import com.heeroes.setset.userGroup.dto.UserGroup;
import com.heeroes.setset.userGroup.model.mapper.UserGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    private final GroupMapper groupMapper;
    private final UserGroupMapper userGroupMapper;

    @Override
    @Transactional
    public GroupResponse create(GroupRequest groupRequest, int userId) {
        String name = groupRequest.getName();
        Group group = Group.builder()
                .name(name)
                .build();
        // 1. 그룹 db에 insert
        groupMapper.insert(group);
        System.out.println("groupId : " + group.getId());
        //2. 그룹원 db에 userId, groupId insert
        UserGroup userGroup = new UserGroup(userId, group.getId());
        userGroupMapper.insert(userGroup);

        return new GroupResponse(group.getId(), name);
    }
}
