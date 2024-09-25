package org.example.service;

import org.example.model.Task;
import org.example.model.response.dto.user.UserRes;
import org.example.model.request.TaskRequest;

import java.util.List;

public interface TaskService {
    UserRes addTask(TaskRequest request);

    Task getTaskById(Long id);

    Task updateTaskById(Long id, TaskRequest request);

    void deleteTaskById(Long id);

    List<Task> getAllTasks();
}
