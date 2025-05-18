package com.hwoo.photogram.web.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileImageResponse {

    private Long id;
    private String caption;
    private String postImageUrl;

    @Builder
    public UserProfileImageResponse(Long id, String caption, String postImageUrl) {
        this.id = id;
        this.caption = caption;
        this.postImageUrl = postImageUrl;
    }
}