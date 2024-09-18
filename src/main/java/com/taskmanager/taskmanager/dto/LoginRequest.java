package com.taskmanager.taskmanager.dto;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
public class LoginRequest {

    private String username;
    private String password;
}
