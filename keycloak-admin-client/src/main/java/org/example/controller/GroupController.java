package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.model.dto.request.GroupRequest;
import org.example.model.response.GroupResponse;
import org.example.service.GroupService;
import org.example.util.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/group")
@AllArgsConstructor
@CrossOrigin
@SecurityRequirement(name = "realm-2")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<APIResponse<GroupResponse>> createGroup(@RequestBody @Valid GroupRequest groupRequest) {
        GroupResponse response = groupService.createGroup(groupRequest);
        APIResponse<GroupResponse> apiResponse = APIResponse.<GroupResponse>builder()
                .status(HttpStatus.CREATED)
                .message("Group Created successfully.")
                .payload(response)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping
    public ResponseEntity<APIResponse<List<GroupResponse>>> getAllGroup() {
        List<GroupResponse> response = groupService.getAllGroup();
        APIResponse<List<GroupResponse>> apiResponse = APIResponse.<List<GroupResponse>>builder()
                .status(HttpStatus.OK)
                .message("Get all group successfully")
                .payload(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<GroupResponse>> getGroupById(@PathVariable UUID id) {
        GroupResponse response = groupService.getGroupById(id);
        APIResponse<GroupResponse> apiResponse = APIResponse.<GroupResponse>builder()
                .status(HttpStatus.OK)
                .message("Get group by id successfully")
                .payload(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<GroupResponse>> updateGroup(@PathVariable UUID id, GroupRequest groupRequest) {
        GroupResponse response = groupService.updateGroup(id, groupRequest);
        APIResponse<GroupResponse> apiResponse = APIResponse.<GroupResponse>builder()
                .status(HttpStatus.OK)
                .message("Update group successfully")
                .payload(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<GroupResponse>> deleteGroup(@PathVariable String id) {
        groupService.deleteGroup(UUID.fromString(id));
        APIResponse<GroupResponse> response = APIResponse.<GroupResponse>builder()
                .status(HttpStatus.OK)
                .message("Group is deleted successfully")
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{groupId}/user/{userId}")
    public ResponseEntity<APIResponse<GroupResponse>> addUserToGroup(@PathVariable UUID userId, @PathVariable UUID groupId) {
        GroupResponse response = groupService.addUserToGroup(userId, groupId);
        APIResponse<GroupResponse> apiResponse = APIResponse.<GroupResponse>builder()
                .status(HttpStatus.CREATED)
                .message("Add User to Group successfully.")
                .payload(response)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{groupId}/users")
    public ResponseEntity<APIResponse<GroupResponse>> getUsersInGroup(@PathVariable UUID groupId) {
        GroupResponse response = groupService.getUsersInGroup(groupId);
        APIResponse<GroupResponse> apiResponse = APIResponse.<GroupResponse>builder()
                .status(HttpStatus.OK)
                .message("Get users in group successfully.")
                .payload(response)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
