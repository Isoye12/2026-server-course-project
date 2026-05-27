package com.example.demo.controller;

import com.example.demo.dto.common.ApiResponse;
import com.example.demo.dto.studyroom.StudyRoomResponse;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-rooms")
public class StudyRoomApiController {
    private final StudyRoomService studyRoomService;

    // 전체 자습실 목록 (오늘 상태)
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudyRoomResponse>>> getAllStudyRooms() {
        List<StudyRoomResponse> rooms = studyRoomService.findAllWithStatus();
        return ResponseEntity.ok(ApiResponse.success("조회 성공", "200", rooms));
    }
}