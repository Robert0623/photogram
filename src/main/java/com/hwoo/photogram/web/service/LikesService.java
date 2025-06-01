package com.hwoo.photogram.web.service;

import com.hwoo.photogram.web.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void likes(Long imageId, Long principalId) {
        likesRepository.mLikes(imageId, principalId);
    }

    @Transactional
    public void unlikes(Long imageId, Long principalId) {
        likesRepository.mUnLikes(imageId, principalId);
    }
}
