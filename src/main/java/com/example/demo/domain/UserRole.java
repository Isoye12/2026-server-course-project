package com.example.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER("USER_ROLE"),
    ADMIN("ROLE_ADMIN");

    private final String key;
}
