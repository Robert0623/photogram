package com.hwoo.photogram.web.api;

import com.hwoo.photogram.config.auth.PrincipalDetails;
import com.hwoo.photogram.web.exception.CommonResponse;
import com.hwoo.photogram.web.request.comment.CommentCreate;
import com.hwoo.photogram.web.response.CommentCreateResponse;
import com.hwoo.photogram.web.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<CommonResponse<CommentCreateResponse>> commentSave(@RequestBody @Valid CommentCreate request,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        CommentCreateResponse response = commentService.commentSave(principalDetails.getUser().getId(), request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success("댓글 등록 성공", response));
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<CommonResponse<Void>> commentDelete(@PathVariable Long id,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentService.commentDelete(id, principalDetails.getUser().getId());
        return ResponseEntity.ok(CommonResponse.success("댓글 삭제 성공"));
    }
}