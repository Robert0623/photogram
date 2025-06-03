package com.hwoo.photogram.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hwoo.photogram.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateResponse {

    private Long id;
    private String content;
    private Long userId;
    private String username;
    private Long imageId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @Builder
    public CommentCreateResponse(Long id, String content, Long userId, String username, Long imageId, LocalDateTime createDate) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.imageId = imageId;
        this.createDate = createDate;
    }

    public static CommentCreateResponse from(Comment comment) {
        return CommentCreateResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getUsername())
                .imageId(comment.getImage().getId())
                .createDate(comment.getCreateDate())
                .build();
    }
}