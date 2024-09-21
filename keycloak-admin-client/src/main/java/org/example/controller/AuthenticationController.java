package org.example.controller;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.model.dto.request.UserRequest;
import org.example.model.response.UserResponse;
import org.example.service.AuthenticationService;

import org.example.util.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("api/v1/authentications")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register with your information")
    public ResponseEntity<APIResponse<UserResponse>> register (@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = authenticationService.registerUser(userRequest);
        APIResponse<UserResponse> apiResponse = APIResponse.<UserResponse>builder()
                .status(HttpStatus.CREATED)
                .message("User registration")
                .payload(userResponse)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
