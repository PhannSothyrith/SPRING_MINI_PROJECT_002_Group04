package org.example.service;

import lombok.AllArgsConstructor;
import org.example.client.KeycloakClient;
import org.example.model.Task;
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
                .groupId(myTask.getGroupId())
                .build();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

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

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}