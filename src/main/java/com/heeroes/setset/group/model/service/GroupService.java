package com.heeroes.setset.group.model.service;

import com.heeroes.setset.group.dto.GroupInviteResponse;
import com.heeroes.setset.group.dto.GroupRequest;
import com.heeroes.setset.group.dto.GroupResponse;
import java.util.List;

public interface GroupService {
    GroupResponse create(GroupRequest groupRequest, int userId);

    GroupInviteResponse invite(int id, List<String> emails, int userId);

    GroupResponse modify(int id, GroupRequest groupRequest, int userId);

    void leaveGroup(int id, int userId);

    List<GroupResponse> findGroupByUserId(int userId);
}
