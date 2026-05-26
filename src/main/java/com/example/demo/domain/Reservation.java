package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id", nullable = false)
    private StudyRoom studyRoom;

    // 예약한 날짜
    @Column(nullable = false)
    private LocalDate date;

    // 시작 교시
    @Column(name = "start_period", nullable = false)
    private int startPeriod;

    // 종료 교시
    @Column(name = "end_period", nullable = false)
    private int endPeriod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Builder
    public Reservation(User user, StudyRoom studyRoom, LocalDate date, int startPeriod, int endPeriod) {
        this.user = user;
        this.studyRoom = studyRoom;
        this.date = date;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.status = ReservationStatus.RESERVED;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }

    public void update(LocalDate date, int startPeriod, int endPeriod) {
        this.date = date;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }
}
