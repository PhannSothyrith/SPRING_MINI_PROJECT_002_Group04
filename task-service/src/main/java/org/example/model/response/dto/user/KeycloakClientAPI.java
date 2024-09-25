package org.example.model.response.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.KeycloakUser;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class KeycloakClientAPI {
    private HttpStatus status;
    private String message;
    private KeycloakUser payload;
    private String time;

    public UserKeycloakRes toRes() {
        return UserKeycloakRes.builder()
                .userId(payload.getUserId())
                .email(payload.getEmail())
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .username(payload.getUserName())
                .build();
    }
}
