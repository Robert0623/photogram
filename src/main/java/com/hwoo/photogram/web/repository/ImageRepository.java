package com.hwoo.photogram.web.repository;

import com.hwoo.photogram.domain.image.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = """
                    SELECT *
                    FROM image
                    WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :principalId) ORDER BY id DESC
                    """, nativeQuery = true)
    Page<Image> mStory(Long principalId, Pageable pageable);
}