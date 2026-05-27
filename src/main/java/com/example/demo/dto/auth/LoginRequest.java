package com.example.demo.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String studentId;   // 학번
    private String password;
}