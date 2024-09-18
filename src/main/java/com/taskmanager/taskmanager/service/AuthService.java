package com.taskmanager.taskmanager.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    public String getAuthenticatedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
