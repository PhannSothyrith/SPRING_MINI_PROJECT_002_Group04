package org.example.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {
    private UUID groupId;
    private String groupName;
    //private UserResponse user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserResponse> userResponseList;
}