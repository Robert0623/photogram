package com.hwoo.photogram.web.service;

import com.hwoo.photogram.domain.user.User;
import com.hwoo.photogram.web.repository.UserRepository;
import com.hwoo.photogram.web.request.auth.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // 1. IoC 2. 트랜잭션 관리
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional // Insert, Update, Delete
    public void signup(Signup request) {
        String encryptedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .email(request.getEmail())
                .password(encryptedPassword)
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
    }
}