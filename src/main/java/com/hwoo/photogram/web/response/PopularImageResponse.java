package com.hwoo.photogram.web.response;

import com.hwoo.photogram.domain.image.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PopularImageResponse {

    private Long userId;
    private String postImageUrl;

    @Builder
    public PopularImageResponse(Long userId, String postImageUrl) {
        this.userId = userId;
        this.postImageUrl = postImageUrl;
    }

    public static PopularImageResponse from(Image image) {
        return PopularImageResponse.builder()
                .userId(image.getUser().getId())
                .postImageUrl(image.getPostImageUrl())
                .build();
    }
}