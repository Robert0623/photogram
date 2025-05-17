package com.hwoo.photogram.web.service;

import com.hwoo.photogram.handler.ex.CustomApiException;
import com.hwoo.photogram.web.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

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
}