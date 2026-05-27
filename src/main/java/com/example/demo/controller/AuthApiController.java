package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.auth.AuthResponse;
import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.SignupRequest;
import com.example.demo.dto.auth.TokenReissueRequest;
import com.example.demo.dto.common.ApiResponse;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.AuthService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApiController {
    private final AuthService authService;
    private final UserService userService;
    private final TokenService tokenService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signUp(@RequestBody SignupRequest request) {
        User user = userService.signUp(request);
        return ResponseEntity.ok(ApiResponse.success(
                "회원가입이 완료되었습니다.",
                "201",
                Map.of("studentId", user.getStudentId(), "name", user.getName())
        ));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", "200", authResponse));
    }

    // AccessToken 재발급
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<?>> reissue(@RequestBody TokenReissueRequest request) {
        String newAccessToken = tokenService.reissueAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(
                "토큰이 재발급되었습니다.",
                "200",
                Map.of("accessToken", newAccessToken)
        ));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        authService.logout(userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponse.success("로그아웃 되었습니다.", "200"));
    }
}