package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.jwt.JwtService;
import com.taskmanager.taskmanager.model.Project;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public void createUser(User user) throws Exception {
        Optional<User> oldUser = userRepo.findByUsername(user.getUsername());
        if (oldUser.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        } else {
            throw new Exception("User already exists. Try using a different username");
        }
    }

    public void updateUser(User user) throws Exception {  // UserName should not be updated...
        Optional<User> oldUser = userRepo.findByUsername(user.getUsername());
        if (oldUser.isEmpty()) {
            throw new Exception("User not found. Update unsuccessful");
        } else {
            User newUser = oldUser.get();
            if (user.getPassword() != null) {
                newUser.setPassword(user.getPassword());
            }

            userRepo.save(newUser);
        }
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserByUsername(String username) throws Exception {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found with username: " + username);
        }
    }

    public List<Project> getProjectByUsername(String username) throws Exception {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getProjectList();
        } else {
            throw new Exception("User not found with username: " + username);
        }
    }

    public String verify(User user){
        // Pass the plain password for authentication
        try {


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            System.out.println(authentication);

            if (authentication.isAuthenticated()) {
                System.out.println("Verify successful...");
                return jwtService.generateToken(user.getUsername());
            } else {
                return "Not Found!";
            }
        }
        catch(Exception e){
            System.out.println(e);
            return "Not Found with Exception";
        }
    }

}
