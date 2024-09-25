package org.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Task;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TaskRequest {
    private String taskName;
    private String description;
    private UUID createdBy;
    private UUID assignedTo;
    private UUID groupId;

    public Task toEntity() {
        return Task.builder()
                .taskId(null)
                .taskName(this.taskName)
                .description(this.description)
                .assignedTo(this.assignedTo)
                .createdBy(this.createdBy)
                .groupId(this.groupId)
                .build();
    }
}
