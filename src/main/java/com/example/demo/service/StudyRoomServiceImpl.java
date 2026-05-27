package com.example.demo.service;

import com.example.demo.domain.ReservationStatus;
import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.studyroom.StudyRoomResponse;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyRoomServiceImpl implements StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StudyRoomResponse> findAllWithStatus() {
        LocalDate today = LocalDate.now();
        List<StudyRoom> rooms = studyRoomRepository.findAll();

        return rooms.stream()
                .map(room -> {
                    // 오늘 날짜에 상태가 RESERVED인 예약이 있으면 사용 불가
                    boolean hasReservation = reservationRepository
                            .existsByStudyRoomIdAndDateAndStatus(room.getId(), today, ReservationStatus.RESERVED);
                    return new StudyRoomResponse(room, !hasReservation);
                })
                .toList();
    }
}