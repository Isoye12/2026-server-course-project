package com.example.demo.service;

import com.example.demo.dto.studyroom.StudyRoomResponse;
import java.util.List;

public interface StudyRoomService {
    List<StudyRoomResponse> findAllWithStatus();
}