package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.jwt.JwtService;
import com.taskmanager.taskmanager.model.Project;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/verify")
    public String verify(@RequestBody User user){
        System.out.println("verify");
        return userService.verify(user);
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return "User created successfully!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return "User updated successfully!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        try {
            return userService.getUserByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{username}/projects")
    public List<Project> getUserProjects(@PathVariable String username) {
        try {
            return userService.getProjectByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



}
