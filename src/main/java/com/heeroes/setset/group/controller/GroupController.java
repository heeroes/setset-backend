package com.heeroes.setset.group.controller;

import com.heeroes.setset.common.Response;
import com.heeroes.setset.group.dto.Group;
import com.heeroes.setset.group.dto.GroupInviteResponse;
import com.heeroes.setset.group.dto.GroupRequest;
import com.heeroes.setset.group.dto.GroupResponse;
import com.heeroes.setset.group.model.service.GroupService;
import com.heeroes.setset.user.utils.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Response<String>> create(@RequestBody GroupRequest groupRequest, @RequestHeader("Authorization") String tokenHeader){
        int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
        groupService.create(groupRequest, userId);
        return ResponseEntity.ok(Response.success("그룹 생성 성공"));
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<Response<?>> invite(@PathVariable int id, @RequestBody List<String> emails, @RequestHeader("Authorization") String tokenHeader) {
        int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
        GroupInviteResponse response = groupService.invite(id, emails, userId);

        return ResponseEntity.ok(Response.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Group>> modify(@PathVariable int id, @RequestBody GroupRequest groupRequest, @RequestHeader("Authorization") String tokenHeader){
        int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
        Group group = groupService.modify(id, groupRequest, userId);
        return ResponseEntity.ok(Response.success(group));
    }

    @DeleteMapping("/{id}/user")
    public ResponseEntity<Response<String>> leaveGroup(@PathVariable int id, @RequestHeader("Authorization") String tokenHeader){
        int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
        groupService.leaveGroup(id, userId);
        return ResponseEntity.ok(Response.success(""));
    }

    @GetMapping("")
    public ResponseEntity<Response<List<GroupResponse>>> getGroupListByUserId( @RequestHeader("Authorization") String tokenHeader){
        int userId = tokenProvider.extractUserId(tokenHeader.substring(7));
        List<GroupResponse> response = groupService.findGroupByUserId(userId);
        return ResponseEntity.ok(Response.success(response));

    }

}
