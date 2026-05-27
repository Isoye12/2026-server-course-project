package com.example.demo.dto.reservation;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.ReservationStatus;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class ReservationResponse {
    private final Long reservationId;
    private final Long studyRoomId;
    private final String studyRoomName;
    private final LocalDate date;
    private final int startPeriod;
    private final int endPeriod;
    private final ReservationStatus status;

    public ReservationResponse(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.studyRoomId = reservation.getStudyRoom().getId();
        this.studyRoomName = reservation.getStudyRoom().getName();
        this.date = reservation.getDate();
        this.startPeriod = reservation.getStartPeriod();
        this.endPeriod = reservation.getEndPeriod();
        this.status = reservation.getStatus();
    }
}