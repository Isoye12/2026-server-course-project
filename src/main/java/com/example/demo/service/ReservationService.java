package com.example.demo.service;

import com.example.demo.domain.Reservation;
import com.example.demo.dto.reservation.ReservationCreateRequest;
import com.example.demo.dto.reservation.ReservationUpdateRequest;
import java.util.List;

public interface ReservationService {
    Reservation createReservation(Long userId, ReservationCreateRequest request);
    List<Reservation> findMyReservations(Long userId);
    Reservation findById(Long userId, Long reservationId);
    Reservation updateReservation(Long userId, Long reservationId, ReservationUpdateRequest request);
    void cancelReservation(Long userId, Long reservationId);
}