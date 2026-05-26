package com.example.demo.repository;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // 내 예약 목록 최신순
    @Query("SELECT r FROM Reservation r JOIN FETCH r.studyRoom WHERE r.user.id = :userId ORDER BY r.id DESC")
    List<Reservation> findByUserIdWithStudyRoom(@Param("userId") Long userId);

    // 특정 자습실의 특정 날짜 예약 현황 조회 (중복 체크 용도)
    @Query("SELECT r FROM Reservation r WHERE r.studyRoom.id = :studyRoomId " +
            "AND r.date = :date AND r.status = :status " +
            "AND NOT (r.endPeriod < :startPeriod OR r.startPeriod > :endPeriod)")
    List<Reservation> findConflicts(@Param("studyRoomId") Long studyRoomId,
                                    @Param("date") LocalDate date,
                                    @Param("startPeriod") int startPeriod,
                                    @Param("endPeriod") int endPeriod,
                                    @Param("status") ReservationStatus status);

    // 특정 자습실의 오늘 날짜 예약 여부 (자습실 상태 조회 용도)
    boolean existsByStudyRoomIdAndDateAndStatus(Long studyRoomId, LocalDate date, ReservationStatus status);
}
