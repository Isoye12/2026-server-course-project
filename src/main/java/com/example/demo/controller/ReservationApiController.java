package com.example.demo.controller;

import com.example.demo.dto.common.ApiResponse;
import com.example.demo.dto.reservation.ReservationCreateRequest;
import com.example.demo.dto.reservation.ReservationResponse;
import com.example.demo.dto.reservation.ReservationUpdateRequest;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationApiController {
    private final ReservationService reservationService;

    // 예약 생성
    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ReservationCreateRequest request) {
        Long userId = userDetails.getUser().getId();
        ReservationResponse response = new ReservationResponse(
                reservationService.createReservation(userId, request)
        );
        return ResponseEntity.ok(ApiResponse.success("예약이 완료되었습니다.", "201", response));
    }

    // 내 예약 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> getMyReservations(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        List<ReservationResponse> responses = reservationService.findMyReservations(userId)
                .stream().map(ReservationResponse::new).toList();
        return ResponseEntity.ok(ApiResponse.success("조회 성공", "200", responses));
    }

    // 예약 상세 조회
    @GetMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationResponse>> getReservation(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reservationId) {
        Long userId = userDetails.getUser().getId();
        ReservationResponse response = new ReservationResponse(
                reservationService.findById(userId, reservationId)
        );
        return ResponseEntity.ok(ApiResponse.success("조회 성공", "200", response));
    }

    // 예약 수정
    @PutMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationResponse>> updateReservation(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reservationId,
            @RequestBody ReservationUpdateRequest request) {
        Long userId = userDetails.getUser().getId();
        ReservationResponse response = new ReservationResponse(
                reservationService.updateReservation(userId, reservationId, request)
        );
        return ResponseEntity.ok(ApiResponse.success("예약이 수정되었습니다.", "200", response));
    }

    // 예약 취소
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<?>> cancelReservation(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reservationId) {
        Long userId = userDetails.getUser().getId();
        reservationService.cancelReservation(userId, reservationId);
        return ResponseEntity.ok(ApiResponse.success("예약이 취소되었습니다.", "200"));
    }
}