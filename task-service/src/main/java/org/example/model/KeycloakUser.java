package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class KeycloakUser {
    private UUID userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String createdAt;
    private String lastModifiedAt;
}
