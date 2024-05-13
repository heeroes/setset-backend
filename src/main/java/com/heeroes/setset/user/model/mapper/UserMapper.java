package com.heeroes.setset.user.model.mapper;

import com.heeroes.setset.user.dto.User;

public interface UserMapper {
    User findByEmail(String email);
    int insert(User user);
}
