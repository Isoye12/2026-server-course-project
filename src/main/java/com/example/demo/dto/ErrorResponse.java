package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final boolean error = true;
    private final boolean success = false;
    private final int status;
    private final String errorCode;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
}