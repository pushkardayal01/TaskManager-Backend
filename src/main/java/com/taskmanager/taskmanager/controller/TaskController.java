package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create/{projectId}")
    public String createTask(@PathVariable Long projectId, @RequestBody Task task) {
        try {
            taskService.createTask(projectId, task);
            return "Task created successfully!";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/update")
    public String updateTask(@RequestBody Task task) {
        try {
            taskService.updateTask(task);
            return "Task updated successfully!";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getAllTasksByProject(@PathVariable Long projectId) {
        try {
            return taskService.getAllTasksByProject(projectId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable Long taskId) {
        try {
            return taskService.getTaskById(taskId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{taskId}/update-status")
    public String updateTaskStatus(@PathVariable Long taskId, @RequestParam String status) {
        try {
            taskService.updateStatusOfTask(taskId, status);
            return "Task status updated successfully!";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
