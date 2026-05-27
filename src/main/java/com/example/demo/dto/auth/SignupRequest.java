package com.example.demo.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String studentId;   // 학번
    private String name;        // 이름
    private String password;
}