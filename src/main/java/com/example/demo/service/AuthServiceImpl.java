package com.example.demo.service;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.config.jwt.TokenProvider;
import com.example.demo.domain.User;
import com.example.demo.dto.auth.AuthResponse;
import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getStudentId(), request.getPassword())
        );

        User user = ((CustomUserDetails) authenticate.getPrincipal()).getUser();

        String accessToken = tokenProvider.generateToken(user, Duration.ofMinutes(jwtProperties.getAccessExpirationMinutes()));
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(jwtProperties.getRefreshExpirationDays()));

        refreshTokenService.saveOrUpdate(user.getId(), refreshToken);

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public void logout(Long userId) {
        refreshTokenService.deleteByUserId(userId);
    }
}