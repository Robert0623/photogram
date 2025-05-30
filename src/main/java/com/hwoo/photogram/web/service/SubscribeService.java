package com.hwoo.photogram.web.service;

import com.hwoo.photogram.handler.ex.CustomApiException;
import com.hwoo.photogram.web.repository.SubscribeRepository;
import com.hwoo.photogram.web.response.SubscribeResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; // Repository는 EntityManager를 구현해서 만들어져 있는 구현체

    @Transactional
    public void subscribe(Long id, Long toUserId) {
        // 동시성 이슈로 "조회 후 존재여부 확인 --> INSERT"는 사용 X
        // --> db에 unique 제약조건 추가 및 try catch 사용 필요
//        Optional<Subscribe> subscribeOptional = subscribeRepository.findByFromUserIdAndToUserId(id, toUserId);
//
//        if (subscribeOptional.isPresent()) {
//            throw new CustomApiException("이미 구독을 하였습니다.");
//        }
//        subscribeRepository.mSubscribe(id, toUserId);

        try {
            subscribeRepository.mSubscribe(id, toUserId);
        } catch (RuntimeException e) {
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }

    @Transactional
    public void unSubscribe(Long id, Long toUserId) {
        subscribeRepository.mUnSubscribe(id, toUserId);
    }

    @Transactional(readOnly = true)
    public List<SubscribeResponse> getSubscribeList(Long userId, Long pageUserId) {
        String sql =
                """
                SELECT u.id
                     , u.username
                     , u.profileImageUrl
                     , CASE
                            WHEN EXISTS (
                                 SELECT 1 
                                 FROM subscribe s2 
                                 WHERE s2.fromUserId = :userId AND s2.toUserId = u.id
                            ) THEN 1
                            ELSE 0
                       END AS subscribeState
                     , CASE
                            WHEN u.id = :userId THEN 1
                            ELSE 0
                       END AS equalUserState
                FROM subscribe s
                JOIN user u ON s.toUserId = u.id
                WHERE s.fromUserId = :pageUserId
                """;

        Query query = em.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("pageUserId", pageUserId);

        // 쿼리 실행 (qlrm 라이브러리 필요 --> dto에 db결과를 맵핑)
        JpaResultMapper resultMapper = new JpaResultMapper();
        List<SubscribeResponse> subscribes = resultMapper.list(query, SubscribeResponse.class);

        return subscribes;
    }
}