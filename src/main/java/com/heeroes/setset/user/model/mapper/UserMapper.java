package com.heeroes.setset.user.model.mapper;

import com.heeroes.setset.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByEmail(String email);
    int insert(User user);
}
