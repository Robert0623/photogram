package com.hwoo.photogram.web.api;

import com.hwoo.photogram.config.auth.PrincipalDetails;
import com.hwoo.photogram.web.exception.CommonResponse;
import com.hwoo.photogram.web.response.MainStoryImageResponse;
import com.hwoo.photogram.web.service.ImageService;
import com.hwoo.photogram.web.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PageableDefault(size = 3) Pageable pageable) {
        Page<MainStoryImageResponse> response = imageService.imageStory(principalDetails.getUser().getId(), pageable);
        return ResponseEntity.ok(CommonResponse.builder()
                .code(1)
                .message("성공")
                .data(response)
                .build());
    }

    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(@PathVariable("imageId") Long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.likes(imageId, principalDetails.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .code(1)
                        .message("좋아요 성공")
                        .build());
    }

    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> unlikes(@PathVariable("imageId") Long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.unlikes(imageId, principalDetails.getUser().getId());
        return ResponseEntity.ok(CommonResponse.builder()
                .code(1)
                .message("좋아요 취소 성공")
                .build());
    }
}