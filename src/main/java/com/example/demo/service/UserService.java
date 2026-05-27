package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.auth.SignupRequest;

public interface UserService {
    User signUp(SignupRequest request);
    User findById(Long id);
}