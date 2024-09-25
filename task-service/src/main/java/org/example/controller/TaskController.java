package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.example.model.request.TaskRequest;
import org.example.model.response.APIResponse;
import org.example.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/tasks")
@SecurityRequirement(name = "realm-2")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> addTasks(@RequestBody TaskRequest request) {
        return APIResponse.createResponse(
                "Successfully create new task",
                taskService.addTask(request)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        return APIResponse.getObjectResponseById(
                "Successfully get task by id : ",
                taskService.getTaskById(id),
                id
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        return APIResponse.getObjectResponse(
                "Successfully get all tasks",
                taskService.getAllTasks()
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        return APIResponse.getObjectResponseById(
                "Successfully update task by id : ",
                taskService.updateTaskById(id, request),
                id
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return APIResponse.deleteResponse(
                "Successfully delete task by id : ",
                id
        );
    }
}
