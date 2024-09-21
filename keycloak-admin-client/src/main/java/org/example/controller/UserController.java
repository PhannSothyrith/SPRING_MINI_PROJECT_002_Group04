package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.model.response.UserResponse;
import org.example.service.AuthenticationService;
import org.example.util.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user/users")
public class UserController {
    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = authenticationService.getAllUsers();
        APIResponse<List<UserResponse>> response = APIResponse.<List<UserResponse>>builder()
                .status(HttpStatus.CREATED)
                .message("User registration")
                .payload(users)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);

    }
    @GetMapping("/{userId}")
        public ResponseEntity<APIResponse<UserResponse>> getUserById(@PathVariable UUID userId) {
        UserResponse user = authenticationService.getUserById(userId);
        APIResponse<UserResponse> response = APIResponse.<UserResponse>builder()
                .status(HttpStatus.OK)
                .message("User registration")
                .payload(user)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }
}
