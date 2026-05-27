package com.example.demo.dto.studyroom;

import com.example.demo.domain.StudyRoom;
import lombok.Getter;

@Getter
public class StudyRoomResponse {
    private final Long id;
    private final String name;
    // 오늘 자습실 사용 가능 여부
    private final boolean available;

    public StudyRoomResponse(StudyRoom studyRoom, boolean available) {
        this.id = studyRoom.getId();
        this.name = studyRoom.getName();
        this.available = available;
    }
}