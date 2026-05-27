package com.example.demo.dto.reservation;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ReservationCreateRequest {
    private Long studyRoomId;
    private LocalDate date;
    private int startPeriod;
    private int endPeriod;
}