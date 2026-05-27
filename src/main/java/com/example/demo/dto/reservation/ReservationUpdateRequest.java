package com.example.demo.dto.reservation;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ReservationUpdateRequest {
    private LocalDate date;
    private int startPeriod;
    private int endPeriod;
}