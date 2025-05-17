package com.hwoo.photogram.web.api;

import com.hwoo.photogram.config.auth.PrincipalDetails;
import com.hwoo.photogram.web.enums.CommonResponseCode;
import com.hwoo.photogram.web.exception.CommonResponse;
import com.hwoo.photogram.web.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PathVariable("toUserId") Long toUserId) {
        subscribeService.subscribe(principalDetails.getUser().getId(), toUserId);

        return ResponseEntity.ok(CommonResponse.builder()
                .code(CommonResponseCode.SUCCESS.getCode())
                .message("구독하기 성공")
                .build());
    }

    @DeleteMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @PathVariable("userId") Long toUserId) {
        subscribeService.unSubscribe(principalDetails.getUser().getId(), toUserId);

        return ResponseEntity.ok(CommonResponse.builder()
                .code(CommonResponseCode.SUCCESS.getCode())
                .message("구독취소하기 성공")
                .build());
    }
}