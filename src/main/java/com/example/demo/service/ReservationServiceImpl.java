package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.dto.reservation.ReservationCreateRequest;
import com.example.demo.dto.reservation.ReservationUpdateRequest;
import com.example.demo.exception.ReservationNotFoundException;
import com.example.demo.exception.StudyRoomNotFoundException;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.StudyRoomRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;

    @Override
    @Transactional
    public Reservation createReservation(Long userId, ReservationCreateRequest request) {
        if (request.getStartPeriod() > request.getEndPeriod()) {
            throw new IllegalArgumentException("시작 교시는 종료 교시보다 클 수 없습니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("회원을 찾을 수 없습니다."));
        StudyRoom studyRoom = studyRoomRepository.findById(request.getStudyRoomId())
                .orElseThrow(() -> new StudyRoomNotFoundException(request.getStudyRoomId()));

        // 중복 예약 체크: 동일 자습실, 날짜, 교시 범위 겹치면 차단
        List<Reservation> conflicts = reservationRepository.findConflicts(
                request.getStudyRoomId(), request.getDate(),
                request.getStartPeriod(), request.getEndPeriod(),
                ReservationStatus.RESERVED
        );
        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("해당 시간대에 이미 예약이 존재합니다.");
        }

        return reservationRepository.save(
                Reservation.builder()
                        .user(user)
                        .studyRoom(studyRoom)
                        .date(request.getDate())
                        .startPeriod(request.getStartPeriod())
                        .endPeriod(request.getEndPeriod())
                        .build()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findMyReservations(Long userId) {
        return reservationRepository.findByUserIdWithStudyRoom(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long userId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
        if (!reservation.getUser().getId().equals(userId)) {
            throw new IllegalStateException("본인의 예약만 조회할 수 있습니다.");
        }
        return reservation;
    }

    @Override
    @Transactional
    public Reservation updateReservation(Long userId, Long reservationId, ReservationUpdateRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new IllegalStateException("본인의 예약만 수정할 수 있습니다.");
        }
        if (reservation.getStatus() != ReservationStatus.RESERVED) {
            throw new IllegalStateException("예약중 상태의 예약만 수정할 수 있습니다.");
        }
        if (request.getStartPeriod() > request.getEndPeriod()) {
            throw new IllegalArgumentException("시작 교시는 종료 교시보다 클 수 없습니다.");
        }

        // 중복 예약 체크 (자기 자신 제외)
        List<Reservation> conflicts = reservationRepository.findConflicts(
                reservation.getStudyRoom().getId(), request.getDate(),
                request.getStartPeriod(), request.getEndPeriod(),
                ReservationStatus.RESERVED
        );
        conflicts.removeIf(r -> r.getId().equals(reservationId));
        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("변경하려는 시간대에 이미 예약이 존재합니다.");
        }

        reservation.update(request.getDate(), request.getStartPeriod(), request.getEndPeriod());
        return reservation;
    }

    @Override
    @Transactional
    public void cancelReservation(Long userId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new IllegalStateException("본인의 예약만 취소할 수 있습니다.");
        }
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("이미 취소된 예약입니다.");
        }
        if (reservation.getStatus() == ReservationStatus.COMPLETED) {
            throw new IllegalStateException("이용 완료된 예약은 취소할 수 없습니다.");
        }

        reservation.cancel();
    }
}