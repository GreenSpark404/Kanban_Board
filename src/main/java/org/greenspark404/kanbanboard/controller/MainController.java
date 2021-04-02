package org.greenspark404.kanbanboard.controller;

import org.greenspark404.kanbanboard.data.model.Task;
import org.greenspark404.kanbanboard.data.model.TaskStatus;
import org.greenspark404.kanbanboard.data.model.User;
import org.greenspark404.kanbanboard.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final TaskService taskService;

    public MainController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getMainPage(@AuthenticationPrincipal User currentUser, Model model) {
        Map<TaskStatus, List<Task>> userTasks =
                taskService.getAssignedUserTasks(currentUser);
        model.addAttribute("todoTasks", userTasks.get(TaskStatus.OPEN));
        model.addAttribute("inProgressTasks", userTasks.get(TaskStatus.IN_PROGRESS));
        model.addAttribute("doneTasks", userTasks.get(TaskStatus.DONE));
        return "main";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

}
