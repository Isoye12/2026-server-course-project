package com.example.demo.exception;

public class StudyRoomNotFoundException extends RuntimeException {
    public StudyRoomNotFoundException(Long id) {
        super("자습실을 찾을 수 없습니다. id: " + id);
    }
}