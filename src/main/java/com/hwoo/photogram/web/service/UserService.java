package com.hwoo.photogram.web.service;

import com.hwoo.photogram.domain.user.User;
import com.hwoo.photogram.handler.ex.CustomApiException;
import com.hwoo.photogram.handler.ex.CustomException;
import com.hwoo.photogram.web.repository.SubscribeRepository;
import com.hwoo.photogram.web.repository.UserRepository;
import com.hwoo.photogram.web.request.user.UserUpdate;
import com.hwoo.photogram.web.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Value("${file.path}")
    private String uploadFolder;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SubscribeRepository subscribeRepository;

    @Transactional
    public User edit(Long userId, UserUpdate request) {

        // 1. 영속화
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("찾을 수 없는 id입니다."));

        // 2. 영속화된 오브젝트를 수정 --> 더티체킹 (업데이트 완료)
        String rawPassword = request.getPassword();

        if (rawPassword != null && !rawPassword.isBlank()) {
            if (rawPassword.length() < 4 || rawPassword.length() > 10) {
                throw new CustomApiException("비밀번호 길이 오류");
            }
            String encryptedPassword = bCryptPasswordEncoder.encode(rawPassword);
            user.editPassword(encryptedPassword);
        }

        user.edit(request.withoutPassword());

        return user;
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(Long pageUserId, Long signinId) {
        User user = userRepository.findById(pageUserId)
                .orElseThrow(() -> new CustomException("해당 프로필 페이지는 없는 페이지입니다."));

        boolean pageOwnerState = Objects.equals(pageUserId, signinId);

        int subscribeState = subscribeRepository.mSubscribeState(signinId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        return UserProfileResponse.from(user, pageOwnerState, subscribeState == 1, subscribeCount);
    }

    @Transactional
    public User profileImageUrlUpdate(Long principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();
        log.info(">>>>> imageFileName={}", imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        File dir = new File(uploadFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        User newUser = userRepository.findById(principalId)
                .orElseThrow(() -> new CustomApiException("해당 유저를 찾을 수 없습니다"));

        newUser.editProfileImageUrl(imageFileName);

        return newUser;
    }
}