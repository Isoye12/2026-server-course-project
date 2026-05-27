package com.example.demo.service;

import com.example.demo.domain.RefreshToken;
import com.example.demo.exception.InvalidTokenException;
import com.example.demo.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional(readOnly = true)
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("유효하지 않은 리프레시 토큰입니다. 다시 로그인해주세요."));
    }

    @Override
    @Transactional
    public RefreshToken saveOrUpdate(Long userId, String refreshToken) {
        return refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(refreshToken))
                .map(refreshTokenRepository::save)
                .orElseGet(() -> refreshTokenRepository.save(
                        new RefreshToken(userId, refreshToken)
                ));
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}