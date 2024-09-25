package org.example.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.example.client.KeycloakClient;
import org.example.model.Task;
import org.example.model.response.dto.group.GroupClientRes;
import org.example.model.response.dto.user.UserKeycloakRes;
import org.example.model.response.dto.user.UserRes;
import org.example.model.request.TaskRequest;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImplement implements TaskService {
    private final TaskRepository taskRepository;
    private final KeycloakClient keycloakClient;

    @CircuitBreaker(name = "kanyeClient", fallbackMethod = "fallingApartDamn")
    @Override
    public UserRes addTask(TaskRequest request) {
        Task myTask = taskRepository.save(request.toEntity());
        keycloakClient.getUserById(request.getCreatedBy());
        return UserRes.builder()
                .taskId(myTask.getTaskId())
                .taskName(myTask.getTaskName())
                .description(myTask.getDescription())
                .createdBy(keycloakClient.getUserById(request.getCreatedBy()).toRes())
                .assignedTo(keycloakClient.getUserById(request.getAssignedTo()).toRes())
                .groupId(keycloakClient.getGroupById(request.getGroupId()).toDamn())
                .build();
    }

    @CircuitBreaker(name = "kanyeClient", fallbackMethod = "fallingApartDamn")
    @Override
    public UserRes getTaskById(Long id) {
        Task myTask = taskRepository.findById(id).orElse(null);
        return UserRes.builder()
                .taskId(myTask.getTaskId())
                .taskName(myTask.getTaskName())
                .description(myTask.getDescription())
                .createdBy(keycloakClient.getUserById(myTask.getCreatedBy()).toRes())
                .assignedTo(keycloakClient.getUserById(myTask.getAssignedTo()).toRes())
                .groupId(keycloakClient.getGroupById(myTask.getGroupId()).toDamn())
                .build();
    }

    @CircuitBreaker(name = "kanyeClient", fallbackMethod = "fallingApartDamnV2")
    @Override
    public Task updateTaskById(Long id, TaskRequest request) {
        Task getTask = taskRepository.findById(id).get();
        getTask.setTaskName(request.getTaskName());
        getTask.setDescription(request.getDescription());
        getTask.setGroupId(request.getGroupId());
        getTask.setAssignedTo(request.getAssignedTo());
        getTask.setCreatedBy(request.getCreatedBy());
        return taskRepository.save(getTask);
    }

    @CircuitBreaker(name = "kanyeClient", fallbackMethod = "fallingApart")
    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @CircuitBreaker(name = "kanyeClient", fallbackMethod = "fallingApart")
    @Override
    public List<UserRes> getAllTasks() {
        return taskRepository.findAll().stream().map(task ->
            UserRes.builder()
                    .taskId(task.getTaskId())
                    .taskName(task.getTaskName())
                    .description(task.getDescription())
                    .createdBy(keycloakClient.getUserById(task.getCreatedBy()).toRes())
                    .assignedTo(keycloakClient.getUserById(task.getAssignedTo()).toRes())
                    .groupId(keycloakClient.getGroupById(task.getGroupId()).toDamn())
                    .build()
        ).toList();
    }

    public List<String> fallingApart(Throwable t) {
        return List.of("Service is busy right now. Please try again later.");
    }

    public UserRes fallingApartDamn(Throwable t) {
        return UserRes.builder()
                .taskId(000L)
                .taskName("Service is busy.")
                .description("Service is not available right now. Please try again later.")
                .build();
    }

    public Task fallingApartDamnV2(Throwable t) {
        return Task.builder()
                .taskId(000L)
                .taskName("Service is busy.")
                .description("Service is not available right now. Please try again later.")
                .build();
    }
}