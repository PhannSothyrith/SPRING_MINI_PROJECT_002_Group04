package org.example.model.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class APIResponse {
    public static ResponseEntity<?> getObjectResponse(String message, Object payload) {
        return ResponseEntity.status(HttpStatus.OK).body(ObjectResponse.builder()
                .status(HttpStatus.OK)
                .message(message)
                .payload(payload)
                .createdAt(LocalDateTime.now())
                .lastModified(LocalDateTime.now())
                .build());
    }

    public static ResponseEntity<?> getObjectResponseById(String message, Object payload, Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ObjectResponse.builder()
                .status(HttpStatus.OK)
                .message(message + id)
                .payload(payload)
                .createdAt(LocalDateTime.now())
                .lastModified(LocalDateTime.now())
                .build());
    }


    public static ResponseEntity<?> createResponse(String message, Object payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ObjectResponse.builder()
                .status(HttpStatus.CREATED)
                .message(message)
                .payload(payload)
                .createdAt(LocalDateTime.now())
                .lastModified(LocalDateTime.now())
                .build());
    }

    public static ResponseEntity<?> deleteResponse(String message, Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(DeleteResponse.builder()
                .message(message + id)
                .status(HttpStatus.OK)
                .createdAt(LocalDateTime.now())
                .build());
    }
}
