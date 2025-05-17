package com.hwoo.photogram.web.service;

import com.hwoo.photogram.handler.ex.CustomValidationException;
import com.hwoo.photogram.domain.user.User;
import com.hwoo.photogram.web.repository.UserRepository;
import com.hwoo.photogram.web.request.user.UserEdit;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User edit(Long userId, UserEdit userEdit) {

        // 1. 영속화
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomValidationException("찾을 수 없는 id입니다."));

        // 2. 영속화된 오브젝트를 수정 --> 더티체킹 (업데이트 완료)
        String rawPassword = userEdit.getPassword();

        if (rawPassword != null && !rawPassword.isBlank()) {
            if (!(4 <= rawPassword.length() && rawPassword.length() <= 10)) {
                throw new CustomValidationException("비밀번호 길이 오류", null);
            }
            String encryptedPassword = bCryptPasswordEncoder.encode(rawPassword);
            user.editPassword(encryptedPassword);
        }

        user.edit(userEdit.withoutPassword());

        return user;
    }
}