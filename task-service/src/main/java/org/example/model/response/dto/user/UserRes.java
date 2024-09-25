package org.example.model.response.dto.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.response.dto.group.GroupClientRes;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRes {
    private Long taskId;
    private String taskName;
    private String description;
    private UserKeycloakRes createdBy;
    private UserKeycloakRes assignedTo;
    private GroupClientRes groupId;
}