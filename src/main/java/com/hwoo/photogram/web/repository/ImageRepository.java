package com.hwoo.photogram.web.repository;

import com.hwoo.photogram.domain.image.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = """
                    SELECT *
                    FROM image
                    WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :principalId) ORDER BY id DESC
                    """, nativeQuery = true)
    Page<Image> mStory(Long principalId, Pageable pageable);

    @Query(value = """
                    SELECT I.*
                    FROM image I
                    JOIN (SELECT imageId
                               , COUNT(imageId) AS likeCount
                          FROM likes
                          GROUP BY imageId) C
                    ON I.id = C.imageId
                    ORDER BY C.likeCount DESC
                    """, nativeQuery = true)
    List<Image> mPopular();
}