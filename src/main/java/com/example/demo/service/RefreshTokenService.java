package com.example.demo.service;

import com.example.demo.domain.RefreshToken;

public interface RefreshTokenService {
    RefreshToken findByRefreshToken(String refreshToken);
    RefreshToken saveOrUpdate(Long userId, String refreshToken);
    void deleteByUserId(Long userId);
}