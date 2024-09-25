package org.example.model.response.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.response.dto.group.GroupClientRes;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class KeycloakGroupAPI {
    private HttpStatus status;
    private String message;
    private GroupClientRes payload;

    public GroupClientRes toDamn() {
        return GroupClientRes.builder()
                .groupId(payload.getGroupId())
                .groupName(payload.getGroupName())
                .build();
    }
}
