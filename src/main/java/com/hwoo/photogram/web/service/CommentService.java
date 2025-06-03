package com.hwoo.photogram.web.service;

import com.hwoo.photogram.domain.comment.Comment;
import com.hwoo.photogram.domain.image.Image;
import com.hwoo.photogram.domain.user.User;
import com.hwoo.photogram.handler.ex.CustomApiException;
import com.hwoo.photogram.web.repository.CommentRepository;
import com.hwoo.photogram.web.repository.ImageRepository;
import com.hwoo.photogram.web.repository.UserRepository;
import com.hwoo.photogram.web.request.comment.CommentCreate;
import com.hwoo.photogram.web.response.CommentCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentCreateResponse commentSave(Long principalId, CommentCreate request) {
        User user = userRepository.findById(principalId)
                .orElseThrow(() -> new CustomApiException("유저 아이디를 찾을 수 없습니다."));

        Image image = imageRepository.findById(request.getImageId())
                .orElseThrow(() -> new CustomApiException("존재하지 않는 이미지 포스트입니다."));

        Comment comment = request.toEntity(user, image);

        commentRepository.save(comment);

        return CommentCreateResponse.from(comment);
    }

    @Transactional
    public void commentDelete(Long commentId, Long principalId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 댓글입니다."));

        Long commentWriterId = comment.getUser().getId();
        if (commentWriterId != principalId) {
            log.error("댓글 삭제 중 댓글 작성자 id와 로그인 id 불일치");
            throw new CustomApiException("댓글 삭제 중 댓글 작성자 id와 로그인 id 불일치");
        }

        commentRepository.deleteById(commentId);
    }
}