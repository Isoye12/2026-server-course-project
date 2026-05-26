package com.example.demo.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperties {
    private String issuer;
    private String secretKey;
    private long accessExpirationMinutes;
    private long refreshExpirationDays;
}
