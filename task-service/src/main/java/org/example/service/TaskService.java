package org.example.service;

import org.example.model.Task;
import org.example.model.request.TaskRequest;

import java.util.List;

public interface TaskService {
    Task addTask(TaskRequest request);

    Task getTaskById(Long id);

    Task updateTaskById(Long id, TaskRequest request);

    void deleteTaskById(Long id);

    List<Task> getAllTasks();
}
