package com.hwoo.photogram.web.service;

import com.hwoo.photogram.web.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subscribe(Long id, Long toUserId) {
        subscribeRepository.mSubscribe(id, toUserId);
    }

    @Transactional
    public void unSubscribe(Long id, Long toUserId) {
        subscribeRepository.mUnSubscribe(id, toUserId);
    }
}