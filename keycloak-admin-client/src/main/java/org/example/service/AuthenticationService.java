package org.example.service;


import org.example.model.entity.User;
import org.example.model.dto.request.UserRequest;
import org.example.model.response.UserResponse;

public interface AuthenticationService {

   UserResponse registerUser(UserRequest userRequest);
}
