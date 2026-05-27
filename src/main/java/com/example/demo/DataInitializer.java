package com.example.demo;

import com.example.demo.domain.*;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.StudyRoomRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 회원 생성 (학번, 이름, 비밀번호)
        User admin = userRepository.save(User.builder()
                .studentId("0000")
                .name("관리자")
                .password(passwordEncoder.encode("admin1234"))
                .role(UserRole.ADMIN)
                .build());

        User user1 = userRepository.save(User.builder()
                .studentId("3404")
                .name("이소예")
                .password(passwordEncoder.encode("user1234"))
                .role(UserRole.USER)
                .build());

        User user2 = userRepository.save(User.builder()
                .studentId("3401")
                .name("홍길동")
                .password(passwordEncoder.encode("user1234"))
                .role(UserRole.USER)
                .build());

        // 자습실 생성
        StudyRoom room1 = studyRoomRepository.save(StudyRoom.builder()
                .name("면접실")
                .build());

        StudyRoom room2 = studyRoomRepository.save(StudyRoom.builder()
                .name("모둠학습실")
                .build());

        StudyRoom room3 = studyRoomRepository.save(StudyRoom.builder()
                .name("프로젝트 1실")
                .build());

        StudyRoom room4 = studyRoomRepository.save(StudyRoom.builder()
                .name("프로젝트 2실")
                .build());

        // 예약 더미 (오늘 날짜 기준)
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        reservationRepository.save(Reservation.builder()
                .user(user1)
                .studyRoom(room1)
                .date(today)
                .startPeriod(7)
                .endPeriod(9)
                .build());

        reservationRepository.save(Reservation.builder()
                .user(user2)
                .studyRoom(room2)
                .date(tomorrow)
                .startPeriod(1)
                .endPeriod(4)
                .build());

        System.out.println("=== 테스트 데이터 초기화 완료 ===");
        System.out.println("관리자 계정: 학번 0000 / 비번 admin1234");
        System.out.println("학생 계정: 학번 3404 / 비번 user1234");
        System.out.println("학생 계정: 학번 3401 / 비번 user1234");
    }
}