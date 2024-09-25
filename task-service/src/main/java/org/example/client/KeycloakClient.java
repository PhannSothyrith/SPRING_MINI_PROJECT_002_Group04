package org.example.client;

import org.example.model.response.dto.user.KeycloakClientAPI;
import org.example.model.response.dto.user.KeycloakGroupAPI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "keycloak-admin-client")
public interface KeycloakClient {
    @GetMapping("/api/v1/user/users/{id}")
    KeycloakClientAPI getUserById(@PathVariable UUID id);

    @GetMapping("/api/v1/group/{id}")
    KeycloakGroupAPI getGroupById(@PathVariable UUID id);
}