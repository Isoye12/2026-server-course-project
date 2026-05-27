package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRole;
import com.example.demo.dto.auth.SignupRequest;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User signUp(SignupRequest request) {
        if (request.getStudentId() == null || request.getStudentId().isBlank()) {
            throw new IllegalArgumentException("학번을 입력해주세요.");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
        if (userRepository.existsByStudentId(request.getStudentId())) {
            throw new IllegalArgumentException("이미 가입된 학번입니다: " + request.getStudentId());
        }
        return userRepository.save(
                User.builder()
                        .studentId(request.getStudentId())
                        .name(request.getName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(UserRole.USER)
                        .build()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("회원을 찾을 수 없습니다."));
    }
}