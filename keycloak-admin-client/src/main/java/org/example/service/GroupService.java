package org.example.service;

import org.example.model.dto.request.GroupRequest;
import org.example.model.response.GroupResponse;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    GroupResponse createGroup(GroupRequest groupRequest);

    List<GroupResponse> getAllGroup();

    GroupResponse getGroupById(UUID id);

    GroupResponse updateGroup(UUID id, GroupRequest groupRequest);

    void deleteGroup(UUID id);

    GroupResponse addUserToGroup(UUID userId, UUID groupId);

    GroupResponse getUsersInGroup(UUID groupId);
}
