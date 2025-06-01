package com.hwoo.photogram.web.service;

import com.hwoo.photogram.domain.image.Image;
import com.hwoo.photogram.domain.user.User;
import com.hwoo.photogram.web.repository.ImageRepository;
import com.hwoo.photogram.web.request.image.ImageUpload;
import com.hwoo.photogram.web.response.MainStoryImageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${file.path}")
    private String uploadFolder;

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true) // 영속성 컨텍스트에서 변경 감지를 해서 더티체킹, flush(반영)
    public Page<MainStoryImageResponse> imageStory(Long principalId, Pageable pageable) {
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        // images에 좋아요 상태 담기
        images.forEach(image -> {
            image.getLikes().forEach(like -> {
                if (like.getUser().getId() == principalId) { // 해당 이미지에 좋아요 한 사람들을 찾아서 현재 로그인한 사람이 좋아요 한 것인지 비교
                    image.editLikeState(true);
                }
            });
        });

        return images.map(MainStoryImageResponse::from);
    }

    @Transactional
    public void imageUpload(User user, ImageUpload request) {
        UUID uuid = UUID.randomUUID(); // uuid
        String imageFileName = uuid + "_" + request.getFile().getOriginalFilename(); // 1.jpg
        log.info(">>>>>> imageFileName={}", imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        File dir = new File(uploadFolder);
        if (!dir.exists()) {
            dir.mkdirs(); // 폴더 생성
        }

        try {
            // 통신 I/O --> 예외가 발생할 수 있다.
            Files.write(imageFilePath, request.getFile().getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image image = request.toEntity(imageFileName, user);
        imageRepository.save(image);
    }
}