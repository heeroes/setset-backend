package com.heeroes.setset.group.controller;

import com.heeroes.setset.Response;
import com.heeroes.setset.group.dto.GroupRequest;
import com.heeroes.setset.group.dto.GroupResponse;
import com.heeroes.setset.group.model.service.GroupService;
import com.heeroes.setset.user.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("")
    public ResponseEntity<Response<GroupResponse>> create(@RequestBody GroupRequest groupRequest, @RequestHeader("Authorization") String tokenHeader){
        int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
        GroupResponse groupResponse = groupService.create(groupRequest, userId);
        return ResponseEntity.ok(Response.success(groupResponse));
    }

}
