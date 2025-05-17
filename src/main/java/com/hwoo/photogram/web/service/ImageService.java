package com.hwoo.photogram.web.service;

import com.hwoo.photogram.domain.image.Image;
import com.hwoo.photogram.domain.user.User;
import com.hwoo.photogram.web.repository.ImageRepository;
import com.hwoo.photogram.web.request.image.ImageUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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