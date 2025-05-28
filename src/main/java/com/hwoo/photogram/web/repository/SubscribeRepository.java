package com.hwoo.photogram.web.repository;

import com.hwoo.photogram.domain.subscribe.Subscribe;
import com.hwoo.photogram.web.response.SubscribeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @Modifying // INSERT, DELETE, UPDATE를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요
    @Query(value = "INSERT INTO subscribe (fromUserId, toUserId, createdDate) VALUES (:fromUserId, :toUserId, NOW())", nativeQuery = true)
    void mSubscribe(Long fromUserId, Long toUserId); // e.g. 1, -1

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void mUnSubscribe(Long fromUserId, Long toUserId); // e.g. 1, -1

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
    int mSubscribeState(Long principalId, Long pageUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery = true)
    int mSubscribeCount(Long pageUserId);

    @Query(value = """
                    SELECT u.id
                         , u.username
                         , u.profileImageUrl
                         , CASE
                                WHEN (SELECT COUNT(*) FROM subscribe s2 WHERE s2.fromUserId = :userId AND s2.toUserId = u.id) > 0 THEN 1
                                ELSE 0
                           END AS subscribeState
                         , CASE
                                WHEN u.id = :userId THEN 1
                                ELSE 0
                           END AS equalUserState
                    FROM subscribe s
                    JOIN user u ON s.toUserId = u.id
                    WHERE s.fromUserId = :pageUserId
                     """, nativeQuery = true)
    List<SubscribeResponse> getSubscribeList(Long userId, Long pageUserId);
}