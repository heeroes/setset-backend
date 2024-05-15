package com.heeroes.setset.group.model.service;

import com.heeroes.setset.group.dto.GroupRequest;
import com.heeroes.setset.group.dto.GroupResponse;

public interface GroupService {
    GroupResponse create(GroupRequest groupRequest, int userId);
}
