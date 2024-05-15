package com.heeroes.setset.group.model.service;

import com.heeroes.setset.group.dto.Group;
import com.heeroes.setset.group.dto.GroupInviteResponse;
import com.heeroes.setset.group.dto.GroupRequest;
import com.heeroes.setset.group.dto.GroupResponse;
import com.heeroes.setset.group.model.mapper.GroupMapper;
import com.heeroes.setset.user.dto.User;
import com.heeroes.setset.user.model.mapper.UserMapper;
import com.heeroes.setset.usergroup.dto.UserGroup;
import com.heeroes.setset.usergroup.model.mapper.UserGroupMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    private final GroupMapper groupMapper;
    private final UserGroupMapper userGroupMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public GroupResponse create(GroupRequest groupRequest, int userId) {
        String name = groupRequest.getName();
        Group group = Group.builder()
                .img(groupRequest.getImg())
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

    @Override
    public GroupInviteResponse invite(int groupId, List<String> emails, int userId){
        if(emails.size() > 5) throw new RuntimeException("한 요청당 이메일은 5개 이하만 가능합니다.");
        List<User> users = new ArrayList<>();
        for(String email : emails){
            User user = userMapper.findByEmail(email);
            if(user == null) throw new RuntimeException("해당 이메일을 가진 사용자가 없습니다.");
            // 예외 : 이미 해당 그룹에 있는 사람이면 x
            UserGroup userGroup = new UserGroup(user.getId(), groupId);
            if(userGroupMapper.isExist(userGroup)){
                throw new RuntimeException("해당 사용자가 이미 그룹에 있습니다.");
            }
            userGroupMapper.insert(userGroup);
            users.add(user);
        }
        return new GroupInviteResponse(groupId, users);
    }

    @Override
    public GroupResponse modify(int id, GroupRequest groupRequest, int userId) {
        String name = groupRequest.getName();
        Group group = Group.builder()
                .id(id)
                .name(name)
                .img(groupRequest.getImg())
                .build();
        // 1. 해당 유저가 그룹에 존재하는지
        if(!userGroupMapper.isExist(new UserGroup(userId, group.getId()))) throw new RuntimeException("그룹 수정은 해당 그룹원만 가능합니다.");
        // 2. 그룹 db에 modify
        groupMapper.modify(group);
        System.out.println("groupName : " + group.getName());

        return new GroupResponse(group.getId(), name);
    }
}
