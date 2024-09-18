package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.model.Project;
import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.repository.ProjectRepo;
import com.taskmanager.taskmanager.repository.TaskRepo;
import com.taskmanager.taskmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthService authService;  // Inject AuthService to get the authenticated user

    // Method to create a task for a specific project
    public void createTask(Long projectId, Task task) {
        String username = authService.getAuthenticatedUsername();  // Get the authenticated username

        // Retrieve the User entity by username
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            // Retrieve the Project entity by projectId
            Optional<Project> project = projectRepo.findById(projectId);
            if (project.isPresent() && project.get().getUser().getUserId().equals(user.get().getUserId())) {
                task.setProject(project.get());  // Associate the task with the project
                taskRepo.save(task);    // Save the task to the database
            } else {
                throw new RuntimeException("Project not found or does not belong to the user");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Method to update a task
    public void updateTask(Task task) {
        String username = authService.getAuthenticatedUsername();


        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {

            Optional<Task> existingTask = taskRepo.findById(task.getTaskId());
            if (existingTask.isPresent() && existingTask.get().getProject().getUser().getUserId().equals(user.get().getUserId())) {
                Task currentTask = existingTask.get();
                currentTask.setTitle(task.getTitle());
                currentTask.setStatus(task.getStatus());
                currentTask.setCompleted(task.isCompleted());

                taskRepo.save(currentTask);
            } else {
                throw new RuntimeException("Task not found or does not belong to the user");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }


    public List<Task> getAllTasksByProject(Long projectId) {
        String username = authService.getAuthenticatedUsername();


        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {

            Optional<Project> project = projectRepo.findById(projectId);
            if (project.isPresent() && project.get().getUser().getUserId().equals(user.get().getUserId())) {
                return project.get().getTasks();  // Return the list of tasks for this project
            } else {
                throw new RuntimeException("Project not found or does not belong to the user");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Method to get a specific task by taskId
    public Task getTaskById(Long taskId) {
        String username = authService.getAuthenticatedUsername();  // Get the authenticated username

        // Retrieve the User entity by username
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            // Ensure the task belongs to the authenticated user and the correct project
            Optional<Task> task = taskRepo.findById(taskId);
            if (task.isPresent() && task.get().getProject().getUser().getUserId().equals(user.get().getUserId())) {
                return task.get();  // Return the task if it belongs to the user
            } else {
                throw new RuntimeException("Task not found or does not belong to the user");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Method to update the status of a task
    public void updateStatusOfTask(Long taskId, String status) {
        String username = authService.getAuthenticatedUsername();  // Get the authenticated username

        // Retrieve the User entity by username
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            // Ensure the task belongs to the authenticated user and the correct project
            Optional<Task> task = taskRepo.findById(taskId);
            if (task.isPresent() && task.get().getProject().getUser().getUserId().equals(user.get().getUserId())) {
                Task existingTask = task.get();
                existingTask.setStatus(status);  // Update the status
                taskRepo.save(existingTask);  // Save the updated task
            } else {
                throw new RuntimeException("Task not found or does not belong to the user");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
