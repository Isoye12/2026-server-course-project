package com.example.demo.config.jwt;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;
    private SecretKey secretKey;

    @PostConstruct
    void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user, Duration expireAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expireAt.toMillis()), user);
    }

    private String makeToken(Date expire, User user) {
        Date now = new Date();
        return Jwts.builder()
                .header().type("JWT").and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiration(expire)
                .subject(user.getStudentId()) // 학번 사용
                .claim("id", user.getId())
                .signWith(secretKey)
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public Long getUserId(String token) {
        return getClaims(token).get("id", Long.class);
    }

    public Authentication getAuthentication(String token) {
        String studentId = getSubject(token);
        User dbUser = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다: " + studentId));
        CustomUserDetails userDetails = new CustomUserDetails(dbUser);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
