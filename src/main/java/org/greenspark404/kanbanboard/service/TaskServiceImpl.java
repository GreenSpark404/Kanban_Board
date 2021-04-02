package org.greenspark404.kanbanboard.service;

import org.greenspark404.kanbanboard.data.TaskRepository;
import org.greenspark404.kanbanboard.data.model.Task;
import org.greenspark404.kanbanboard.data.model.TaskStatus;
import org.greenspark404.kanbanboard.data.model.User;
import org.greenspark404.kanbanboard.mvc.TaskFormData;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Map<TaskStatus, List<Task>> getAssignedUserTasks(User user) {
        List<Task> userTasks = taskRepository.findByAssignee(user,
                Sort.by(Sort.Direction.DESC, "lastModificationDate"));

        return userTasks.stream().collect(Collectors.groupingBy(Task::getStatus));
    }

    @Override
    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(IllegalArgumentException::new);
    }

    public Task createTask(TaskFormData taskFormData) {
        User currentUser = (User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = new Task();
        task.setStatus(TaskStatus.OPEN);
        task.setAuthor(currentUser);
        task.setCreationDate(LocalDateTime.now());
        updateTask(taskFormData, task);

        return task;
    }

    public void updateTask(TaskFormData taskFormData, Task task) {
        task.setAssignee(taskFormData.getAssignee());
        task.setTitle(taskFormData.getTitle());
        task.setDescription(taskFormData.getDescription());

        saveTask(task);
    }

    @Override
    public void changeTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(IllegalArgumentException::new);
        task.setStatus(status);

        saveTask(task);
    }

    @Override
    public List<Task> searchTasks(String pattern) {
        return taskRepository.findByPattern(pattern);
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    private void saveTask(Task task) {
        task.setLastModificationDate(LocalDateTime.now());
        taskRepository.save(task);
    }
}
