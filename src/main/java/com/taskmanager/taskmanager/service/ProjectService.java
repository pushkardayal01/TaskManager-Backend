package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.model.Project;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.repository.ProjectRepo;
import com.taskmanager.taskmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthService authService;  // Inject AuthService to get the authenticated user



    public void createProject(Project project) {
        String username = authService.getAuthenticatedUsername();


        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            project.setUser(user.get());  // Associate the project with the authenticated user
            projectRepo.save(project);    // Save the project to the database
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Method to update a project
    public void updateProject(Project project) {
        String username = authService.getAuthenticatedUsername();  // Get the authenticated username

        // Retrieve the User entity by username
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            project.setUser(user.get());  // Re-associate the project with the user
            projectRepo.save(project);    // Update the project
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Method to get all projects for the authenticated user
    public List<Project> getAllProjectsByUser() {
        String username = authService.getAuthenticatedUsername();  // Get the authenticated username

        // Retrieve the User entity by username
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getProjectList();  // Return the list of projects for this user
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Method to get a specific project by projectId for the authenticated user
    public Project getProjectById(Long projectId) {
        String username = authService.getAuthenticatedUsername();  // Get the authenticated username

        // Retrieve the User entity by username
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            // Ensure the project belongs to the authenticated user
            Optional<Project> project = projectRepo.findById(projectId);
            if (project.isPresent() && project.get().getUser().getUserId().equals(user.get().getUserId())) {
                return project.get();  // Return the project if it belongs to the user
            } else {
                throw new RuntimeException("Project not found or does not belong to the user");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Method to update the status of a project
    public void updateStatusOfProject(Long projectId, String status) {
        String username = authService.getAuthenticatedUsername();  // Get the authenticated username

        // Retrieve the User entity by username
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            // Ensure the project belongs to the authenticated user
            Optional<Project> project = projectRepo.findById(projectId);
            if (project.isPresent() && project.get().getUser().getUserId().equals(user.get().getUserId())) {
                Project existingProject = project.get();
                existingProject.setStatus(status);  // Update the status
                projectRepo.save(existingProject);  // Save the updated project
            } else {
                throw new RuntimeException("Project not found or does not belong to the user");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
