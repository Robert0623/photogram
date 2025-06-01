package com.hwoo.photogram.web.response;

import com.hwoo.photogram.domain.image.Image;
import com.hwoo.photogram.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MainStoryImageResponse {

    private Long id;
    private String caption;
    private String postImageUrl;
    private LocalDateTime createdDate;
    private UserResponse userResponse;
    private boolean likeState;
    private int likeCount;

    @Builder
    public MainStoryImageResponse(Long id, String caption, String postImageUrl, LocalDateTime createdDate, UserResponse userResponse, boolean likeState, int likeCount) {
        this.id = id;
        this.caption = caption;
        this.postImageUrl = postImageUrl;
        this.createdDate = createdDate;
        this.userResponse = userResponse;
        this.likeState = likeState;
        this.likeCount = likeCount;
    }

    public static MainStoryImageResponse from(Image image) {
        return MainStoryImageResponse.builder()
                .id(image.getId())
                .caption(image.getCaption())
                .postImageUrl(image.getPostImageUrl())
                .createdDate(image.getCreatedDate())
                .userResponse(UserResponse.from(image.getUser()))
                .likeState(image.isLikeState())
                .likeCount(image.getLikeCount())
                .build();
    }

    @Getter
    private static class UserResponse {
        private Long id;
        private String name;
        private String profileImageUrl;

        @Builder
        public UserResponse(Long id, String name, String profileImageUrl) {
            this.id = id;
            this.name = name;
            this.profileImageUrl = profileImageUrl;
        }

        public static UserResponse from(User user) {
            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .profileImageUrl(user.getProfileImageUrl())
                    .build();
        }
    }
}