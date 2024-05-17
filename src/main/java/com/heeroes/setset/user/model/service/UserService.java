package com.heeroes.setset.user.model.service;

import com.heeroes.setset.user.dto.User;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User modifyProfile(int userId, MultipartFile userImage, String nickname) throws IOException;
}
