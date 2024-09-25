package org.example.model.response.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserKeycloakRes {
    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
