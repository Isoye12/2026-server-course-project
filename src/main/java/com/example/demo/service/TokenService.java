package com.example.demo.service;

public interface TokenService {
    String reissueAccessToken(String refreshToken);
}