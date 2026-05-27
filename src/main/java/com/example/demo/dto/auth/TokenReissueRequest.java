package com.example.demo.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenReissueRequest {
    private String refreshToken;
}