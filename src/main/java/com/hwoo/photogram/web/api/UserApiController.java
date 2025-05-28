package com.hwoo.photogram.web.api;

import com.hwoo.photogram.config.auth.PrincipalDetails;
import com.hwoo.photogram.domain.user.User;
import com.hwoo.photogram.handler.ex.CustomValidationException;
import com.hwoo.photogram.web.exception.CommonResponse;
import com.hwoo.photogram.web.request.user.UserUpdate;
import com.hwoo.photogram.web.response.SubscribeResponse;
import com.hwoo.photogram.web.service.SecurityService;
import com.hwoo.photogram.web.service.SubscribeService;
import com.hwoo.photogram.web.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final SecurityService securityService;
    private final SubscribeService subscribeService;

    @GetMapping("/api/{pageUserId}/subscribe")
    public CommonResponse<?> subscribeList(@PathVariable Long pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeResponse> response = subscribeService.getSubscribeList(principalDetails.getUser().getId(), pageUserId);

        return CommonResponse.builder()
                .code(1)
                .message("구독자 정보 리스트 가져오기 성공")
                .data(response)
                .build();
    }

    @PatchMapping("/api/user/{userId}")
    public CommonResponse<?> update(@PathVariable("userId") Long userId,
                                    @Valid UserUpdate request,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            throw new CustomValidationException("회원정보수정 유효성 검사 실패", errorMap);
        }

        User user = userService.edit(userId, request);
        securityService.reAuthenticate(user);
        return CommonResponse
                .builder()
                .code(1)
                .message("회원 수정 완료")
                .build();
    }

}