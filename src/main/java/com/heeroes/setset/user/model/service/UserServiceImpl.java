package com.heeroes.setset.user.model.service;

import com.heeroes.setset.attachedfile.dto.AttachedFile;
import com.heeroes.setset.user.dto.User;
import com.heeroes.setset.user.dto.UserInfoResponse;
import com.heeroes.setset.user.model.mapper.UserMapper;
import com.heeroes.setset.util.S3Util;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final S3Util s3Util;
    private final UserMapper userMapper;
    @Override
    @Transactional
    public User modifyProfile(int userId, MultipartFile userImage, String nickname) throws IOException {
        User user = userMapper.findById(userId);
        // 기존 이미지가 있는 경우 삭제
        if(user.getImageKey() != null){
            s3Util.deleteImageFromS3(user.getImageKey());
        }
        String path = "user/" + userId + "/";
        // 이미지 업로드
        AttachedFile upload = s3Util.upload(userImage, path);
        user.setNickname(nickname);
        user.setOriginalName(upload.getOriginalName());
        user.setImageKey(upload.getImageKey());
        //user DB update
        userMapper.update(user);
        return user;

    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(int userId) {
        User user = userMapper.findById(userId);
        return UserInfoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .imageKey(user.getImageKey())
                .nickname(user.getNickname())
                .build();
    }
}
