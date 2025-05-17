package com.hwoo.photogram.web.repository;

import com.hwoo.photogram.domain.subscribe.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @Modifying // INSERT, DELETE, UPDATE를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요
    @Query(value = "INSERT INTO subscribes (fromUserId, toUserId, createdDate) VALUES (:fromUserId, :toUserId:, NOW())", nativeQuery = true)
    void mSubscribe(Long fromUserId, Long toUserId); // e.g. 1, -1

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void mUnSubscribe(Long fromUserId, Long toUserId); // e.g. 1, -1
}