package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {
    private final boolean error;
    private final boolean success;
    private final String message;
    private final String code;
    private final T data;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;

    private ApiResponse(boolean error, boolean success, String message, String code, T data) {
        this.error = error;
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(String message, String code, T data) {
        return new ApiResponse<>(false, true, message, code, data);
    }

    public static <T> ApiResponse<T> success(String message, String code) {
        return new ApiResponse<>(false, true, message, code, null);
    }

    public static <T> ApiResponse<T> fail(String message, String code) {
        return new ApiResponse<>(true, false, message, code, null);
    }
}
