package org.example.client;

import org.example.model.KeycloakUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "keycloak-admin-client")
public interface KeycloakClient {
    @GetMapping("{id}")
    KeycloakUser getRandom(@PathVariable("id") UUID id);
}
