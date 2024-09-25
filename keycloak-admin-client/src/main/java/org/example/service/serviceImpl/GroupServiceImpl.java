package org.example.service.serviceImpl;

import jakarta.ws.rs.core.Response;
import org.example.exception.NotFoundException;
import org.example.model.dto.request.GroupRequest;
import org.example.model.response.GroupResponse;
import org.example.model.response.UserResponse;
import org.example.service.GroupService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    public GroupServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }
    @Override
    public GroupResponse createGroup(GroupRequest groupRequest) {

        GroupRepresentation group = new GroupRepresentation();
        group.setName(groupRequest.getGroupName());
        Response response = keycloak.realm(realm).groups().add(group);

        List<GroupRepresentation> groupList = keycloak.realm(realm).groups().groups();

       GroupRepresentation createGroup = groupList.stream()
               .filter(groups -> groupRequest.getGroupName().equals(groups.getName()))
               .findFirst()
               .orElseThrow(null);

       GroupResponse groupResponse = GroupResponse.builder()
               .groupId(UUID.fromString(createGroup.getId()))
               .groupName(groupRequest.getGroupName())
               .build();
       return groupResponse;
    }

    @Override
    public List<GroupResponse> getAllGroup() {
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        List<GroupRepresentation> groupRepresentations = groupsResource.groups();

        List<GroupResponse> groupResponses = groupRepresentations.stream().map(groupRepresentation -> {
            GroupResponse response = new GroupResponse();
            response.setGroupId(UUID.fromString(groupRepresentation.getId()));
            response.setGroupName(groupRepresentation.getName());
            return response;
        }).collect(Collectors.toList());

        return groupResponses;
    }

    @Override
    public GroupResponse getGroupById(UUID id) {
        GroupResource groupResource = keycloak.realm(realm).groups().group(id.toString());

        if (groupResource == null || groupResource.toRepresentation() == null) {
            throw new NotFoundException("Group with id " + id + " doesn't exist.");
        }
        GroupRepresentation groupRepresentation = groupResource.toRepresentation();

        return prepareGroupResponse(groupRepresentation);
    }

    @Override
    public GroupResponse updateGroup(UUID id, GroupRequest groupRequest) {
        GroupResource groupResource = keycloak.realm(realm).groups().group(id.toString());
        GroupRepresentation groupRepresentation = groupResource.toRepresentation();

        if (groupRepresentation == null) {
            throw new NotFoundException("Group not found with id " + id);
        }

        groupRepresentation.setName(groupRequest.getGroupName());
        groupResource.update(groupRepresentation);
        return prepareGroupResponse(groupRepresentation);
    }

    @Override
    public void deleteGroup(UUID id) {
        GroupResource groupResource = keycloak.realm(realm).groups().group(id.toString());
        GroupRepresentation groupRepresentation = groupResource.toRepresentation();
        if (groupRepresentation == null) {
            throw new NotFoundException("Group not found with id " + id);
        }
        groupResource.remove();
    }

    @Override
    public GroupResponse addUserToGroup(UUID userId, UUID groupId) {

            GroupRepresentation groupRepresentation = keycloak.realm(realm).groups().group(groupId.toString()).toRepresentation();
            UserResource userResource = keycloak.realm(realm).users().get(userId.toString());
            UserRepresentation userRepresentation = userResource.toRepresentation();

            userResource.joinGroup(groupId.toString());

            UserResponse userResponse = UserResponse.builder()
                    .userId(UUID.fromString(userRepresentation.getId()))
                    .userName(userRepresentation.getUsername())
                    .email(userRepresentation.getEmail())
                    .firstName(userRepresentation.getFirstName())
                    .lastName(userRepresentation.getLastName())
                    .createdAt(userRepresentation.getCreatedTimestamp().toString())
                    .lastModifiedAt(LocalDateTime.now().toString())
                    .build();

            List<UserResponse> userResponseList = new ArrayList<>();
            userResponseList.add(userResponse);

            return GroupResponse.builder()
                    .groupId(UUID.fromString(groupRepresentation.getId()))
                    .groupName(groupRepresentation.getName())
                    .user(userResponseList)
                    .build();
    }

    @Override
    public GroupResponse getUsersInGroup(UUID groupId) {
        GroupRepresentation groupRepresentation = keycloak.realm(realm).groups().group(groupId.toString()).toRepresentation();
        List<UserRepresentation> userRepresentationsList = keycloak.realm(realm).groups().group(groupId.toString()).members();

        List<UserResponse> userResponse = userRepresentationsList.stream().map(user -> UserResponse.builder()
                .userId(UUID.fromString(user.getId()))
                .userName(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedTimestamp().toString())
                .lastModifiedAt(LocalDateTime.now().toString())
                .build()).collect(Collectors.toList());
        GroupResponse groupResponse = GroupResponse.builder()
                .groupId(UUID.fromString(groupRepresentation.getId()))
                .groupName(groupRepresentation.getName())
                .user(userResponse)
                .build();
        return groupResponse;
    }

    private GroupResponse prepareGroupResponse(GroupRepresentation groupRepresentation) {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setGroupId(UUID.fromString(groupRepresentation.getId()));
        groupResponse.setGroupName(groupRepresentation.getName());
        return groupResponse;
    }
}
