package org.greenspark404.kanbanboard.controller;

import org.greenspark404.kanbanboard.data.model.Role;
import org.greenspark404.kanbanboard.data.model.Task;
import org.greenspark404.kanbanboard.data.model.TaskStatus;
import org.greenspark404.kanbanboard.data.model.User;
import org.greenspark404.kanbanboard.mvc.TaskFormData;
import org.greenspark404.kanbanboard.service.TaskService;
import org.greenspark404.kanbanboard.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class TaskController {

    private final TaskService taskService;

    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/getTask/{taskId}")
    public String getTask(@PathVariable Long taskId,
                          @AuthenticationPrincipal User currentUser,
                          Model model) {
        Task task = taskService.findTaskById(taskId);

        model.addAttribute("task", task);
        model.addAttribute("canEdit", canUserEditTask(task, currentUser));
        return "task";
    }

    @GetMapping("/createTask")
    public String createTask(@AuthenticationPrincipal User currentUser, Model model) {
        TaskFormData taskFormData = new TaskFormData();
        Set<User> availableAssignees = taskFormData.getAvailableAssignees();
        userService.getAvailableAssignees(currentUser)
                .forEach(availableAssignees::add);
        taskFormData.setAssignee(currentUser);

        model.addAttribute("taskForm", taskFormData);
        return "taskEditor";
    }

    @PostMapping("/createTask")
    public String createTask(@Valid @ModelAttribute("taskForm") TaskFormData taskFormData) {
        taskService.createTask(taskFormData);
        return "redirect:/";
    }

    @PostMapping("/changeTaskStatus")
    public String updateTaskStatus(@RequestParam Long taskId,
                                   @RequestParam TaskStatus taskStatus,
                                   @AuthenticationPrincipal User currentUser,
                                   Model model) {
        taskService.changeTaskStatus(taskId, taskStatus);
        return getTask(taskId, currentUser, model);
    }

    @GetMapping("/editTask/{taskId}")
    public String editTask(@PathVariable Long taskId, Model model,
                           @AuthenticationPrincipal User currentUser) {
        Task task = taskService.findTaskById(taskId);
        TaskFormData taskFormData = new TaskFormData();

        if (!canUserEditTask(task, currentUser)) {
            return getTask(taskId, currentUser, model);
        }

        Set<User> availableAssignees = taskFormData.getAvailableAssignees();
        userService.getAvailableAssignees(currentUser)
                .forEach(availableAssignees::add);
        taskFormData.setAssignee(task.getAssignee());
        taskFormData.setTitle(task.getTitle());
        taskFormData.setDescription(task.getDescription());

        model.addAttribute("taskForm", taskFormData);
        return "taskEditor";
    }

    @PostMapping("/editTask")
    public String editTask(@RequestParam Long taskId, @RequestParam String action,
                           @Valid @ModelAttribute("taskForm") TaskFormData taskFormData,
                           @AuthenticationPrincipal User currentUser, Model model) {
        Task task = taskService.findTaskById(taskId);
        if (canUserEditTask(task, currentUser)) {
            switch (action) {
                case "edit":
                    taskService.updateTask(taskFormData, task);
                    break;
                case "delete":
                    taskService.deleteTask(task);
                    return "redirect:/";
            }
        }

        return getTask(taskId, currentUser, model);
    }

    @PostMapping("/searchTasks")
    public String searchTasks(@RequestParam String pattern, Model model) {
        List<Task> tasks = taskService.searchTasks(pattern);
        model.addAttribute("tasks", tasks);
        return "taskList";
    }

    private boolean canUserEditTask(Task task, User user) {
        return user.equals(task.getAuthor())
                || user.getRoles().contains(Role.ADMIN);
    }

}
