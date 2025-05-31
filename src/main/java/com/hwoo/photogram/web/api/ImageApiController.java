package com.hwoo.photogram.web.api;

import com.hwoo.photogram.config.auth.PrincipalDetails;
import com.hwoo.photogram.web.exception.CommonResponse;
import com.hwoo.photogram.web.response.MainStoryImageResponse;
import com.hwoo.photogram.web.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageService imageService;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<MainStoryImageResponse> response = imageService.imageStory(principalDetails.getUser().getId());
        return ResponseEntity.ok(CommonResponse.builder()
                .code(1)
                .message("성공")
                .data(response)
                .build());
    }
}