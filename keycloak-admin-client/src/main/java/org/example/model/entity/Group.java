package org.example.model.entity;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    private UUID groupId;
    private String groupName;
}
