package org.greenspark404.kanbanboard.service;

import org.greenspark404.kanbanboard.data.model.Task;
import org.greenspark404.kanbanboard.data.model.TaskStatus;
import org.greenspark404.kanbanboard.data.model.User;
import org.greenspark404.kanbanboard.mvc.TaskFormData;

import java.util.List;
import java.util.Map;

public interface TaskService {

    Task findTaskById(Long taskId);

    Task createTask(TaskFormData taskFormData);

    void updateTask(TaskFormData taskFormData, Task task);

    void deleteTask(Task task);

    void changeTaskStatus(Long taskId, TaskStatus status);

    List<Task> searchTasks(String pattern);

    Map<TaskStatus, List<Task>> getAssignedUserTasks(User user);
}
