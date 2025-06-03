package com.hwoo.photogram.web.request.comment;

import com.hwoo.photogram.domain.comment.Comment;
import com.hwoo.photogram.domain.image.Image;
import com.hwoo.photogram.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreate {

    private String content;
    private Long imageId;

    @Builder
    public CommentCreate(String content, Long imageId) {
        this.content = content;
        this.imageId = imageId;
    }

    public Comment toEntity(User user, Image image) {
        return Comment.builder()
                .content(this.content)
                .user(user)
                .image(image)
                .build();
    }
}