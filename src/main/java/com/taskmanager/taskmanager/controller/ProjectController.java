package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.model.Project;
import com.taskmanager.taskmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public String createProject(@RequestBody Project project) {
        try {
            projectService.createProject(project);
            return "Project created successfully!";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/update")
    public String updateProject(@RequestBody Project project) {
        try {
            projectService.updateProject(project);
            return "Project updated successfully!";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/all")
    public List<Project> getAllProjects() {
        return projectService.getAllProjectsByUser();
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(@PathVariable Long projectId) {
        try {
            return projectService.getProjectById(projectId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{projectId}/update-status")
    public String updateProjectStatus(@PathVariable Long projectId, @RequestParam String status) {
        try {
            projectService.updateStatusOfProject(projectId, status);
            return "Project status updated successfully!";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
