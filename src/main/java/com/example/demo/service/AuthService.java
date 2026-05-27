package com.example.demo.service;

import com.example.demo.dto.auth.AuthResponse;
import com.example.demo.dto.auth.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    void logout(Long userId);
}