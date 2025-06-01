package com.hwoo.photogram.web.repository;

import com.hwoo.photogram.domain.likes.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes (imageId, userId, createDate) VALUES (:imageId, :principalId, NOW())", nativeQuery = true)
    int mLikes(Long imageId, Long principalId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE imageId = :imageId AND userId = :principalId", nativeQuery = true)
    int mUnLikes(Long imageId, Long principalId);
}
