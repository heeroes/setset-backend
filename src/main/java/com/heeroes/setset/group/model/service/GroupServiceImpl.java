package com.heeroes.setset.group.model.service;

import com.heeroes.setset.article.model.service.ArticleService;
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
    private final ArticleService articleService;

    @Override
    @Transactional
    public void create(GroupRequest groupRequest, int userId) {
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
    }

    @Override
    @Transactional
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
    public Group modify(int id, GroupRequest groupRequest, int userId) {
        String name = groupRequest.getName();
        Group group = Group.builder()
                .id(id)
                .name(name)
                .build();
        // 1. 해당 유저가 그룹에 존재하는지
        if(!userGroupMapper.isExist(new UserGroup(userId, group.getId()))) throw new RuntimeException("그룹 수정은 해당 그룹원만 가능합니다.");
        // 2. 그룹 db에 modify
        groupMapper.modify(group);
        System.out.println("groupName : " + group.getName());

        return new Group(group.getId(), name);
    }

    @Override
    @Transactional
    public void leaveGroup(int groupId, int userId) {
        userGroupMapper.delete(new UserGroup(userId, groupId));
        int userCnt = userGroupMapper.countGroupUser(groupId);
        System.out.println("userCnt in group: "  + userCnt);
        //그룹에 아무도 없다면
        if(userCnt == 0){
            // 그룹 관련 게시글 다 삭제
            System.out.println("그룹 게시글 다 삭제");
            articleService.deleteAllByGroupId(groupId);
            // 그룹 정보까지 삭제
            System.out.println("그룹 정보 삭제");
            groupMapper.deleteById(groupId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponse> findGroupByUserId(int userId) {
        List<Group> groups = userGroupMapper.findGroupByUserId(userId);
        List<GroupResponse> responses = new ArrayList<>();
        for(Group group : groups){
            int cnt = userGroupMapper.countGroupUser(group.getId());
            responses.add(GroupResponse.builder()
                    .userCnt(cnt)
                    .id(group.getId())
                    .name(group.getName())
                    .build());
        }
        return responses;
    }


}
