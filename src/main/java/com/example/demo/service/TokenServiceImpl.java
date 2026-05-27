package com.example.demo.service;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.config.jwt.TokenProvider;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional(readOnly = true)
    public String reissueAccessToken(String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalStateException("유효하지 않은 리프레시 토큰입니다. 다시 로그인해주세요.");
        }
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);
        return tokenProvider.generateToken(user, Duration.ofMinutes(jwtProperties.getAccessExpirationMinutes()));
    }
}