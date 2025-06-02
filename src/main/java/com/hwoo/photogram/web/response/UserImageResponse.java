package com.hwoo.photogram.web.response;

import com.hwoo.photogram.domain.image.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserImageResponse {

    private Long id;
    private String caption;
    private String postImageUrl;
    private int likeCount;

    @Builder
    public UserImageResponse(Long id, String caption, String postImageUrl, int likeCount) {
        this.id = id;
        this.caption = caption;
        this.postImageUrl = postImageUrl;
        this.likeCount = likeCount;
    }

    public static UserImageResponse from(Image image) {
        return UserImageResponse.builder()
                .id(image.getId())
                .caption(image.getCaption())
                .postImageUrl(image.getPostImageUrl())
                .likeCount(image.getLikes().size())
                .build();
    }
}