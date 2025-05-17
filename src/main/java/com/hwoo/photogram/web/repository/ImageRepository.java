package com.hwoo.photogram.web.repository;

import com.hwoo.photogram.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}