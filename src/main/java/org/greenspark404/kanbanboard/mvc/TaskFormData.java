package org.greenspark404.kanbanboard.mvc;

import org.greenspark404.kanbanboard.data.model.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class TaskFormData {

    @NotNull
    private User assignee;

    @NotEmpty
    @Size(max = 255)
    private String title;

    @Size(max = 4096)
    private String description;

    private final Set<User> availableAssignees = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Set<User> getAvailableAssignees() {
        return availableAssignees;
    }
}
