package com.heeroes.setset.group.dto;

import com.heeroes.setset.user.dto.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupInviteResponse {
    private int groupId;
    List<User> users;
}
