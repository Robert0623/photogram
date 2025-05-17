package com.hwoo.photogram.web.service;

import com.hwoo.photogram.domain.user.User;
import com.hwoo.photogram.handler.ex.CustomApiException;
import com.hwoo.photogram.handler.ex.CustomException;
import com.hwoo.photogram.web.repository.UserRepository;
import com.hwoo.photogram.web.request.user.UserEdit;
import com.hwoo.photogram.web.response.UserProfileResponse;
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
                .orElseThrow(() -> new CustomApiException("찾을 수 없는 id입니다."));

        // 2. 영속화된 오브젝트를 수정 --> 더티체킹 (업데이트 완료)
        String rawPassword = userEdit.getPassword();

        if (rawPassword != null && !rawPassword.isBlank()) {
            if (!(4 <= rawPassword.length() && rawPassword.length() <= 10)) {
                throw new CustomApiException("비밀번호 길이 오류");
            }
            String encryptedPassword = bCryptPasswordEncoder.encode(rawPassword);
            user.editPassword(encryptedPassword);
        }

        user.edit(userEdit.withoutPassword());

        return user;
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfiles(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("해당 프로필 페이지는 없는 페이지입니다."));

        return UserProfileResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .website(user.getWebsite())
                .phone(user.getPhone())
                .gender(user.getGender())
                .profileImageUrl(user.getProfileImageUrl())
                .images(user.getImages())
                .build();
    }
}