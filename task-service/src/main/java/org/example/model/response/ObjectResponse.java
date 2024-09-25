package org.example.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ObjectResponse<T> {
    private HttpStatus status;
    private String message;
    private T payload;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
}
