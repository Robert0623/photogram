package com.hwoo.photogram.web.response;

import com.hwoo.photogram.domain.comment.Comment;
import com.hwoo.photogram.domain.image.Image;
import com.hwoo.photogram.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MainStoryImageResponse {

    private Long id;
    private String caption;
    private String postImageUrl;
    private LocalDateTime createdDate;
    private UserResponse userResponse;
    private boolean likeState;
    private int likeCount;
    private List<CommentResponse> comments;

    @Builder
    public MainStoryImageResponse(Long id, String caption, String postImageUrl, LocalDateTime createdDate, UserResponse userResponse, boolean likeState, int likeCount, List<CommentResponse> comments) {
        this.id = id;
        this.caption = caption;
        this.postImageUrl = postImageUrl;
        this.createdDate = createdDate;
        this.userResponse = userResponse;
        this.likeState = likeState;
        this.likeCount = likeCount;
        this.comments = comments;
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
                .comments(image.getComments().stream()
                        .map(comment -> CommentResponse.from(comment))
                        .toList())
                .build();
    }

    @Getter
    private static class CommentResponse {

        private Long id;
        private String content;
        private Long userId;
        private String username;

        @Builder
        public CommentResponse(Long id, String content, Long userId, String username) {
            this.id = id;
            this.content = content;
            this.userId = userId;
            this.username = username;
        }

        public static CommentResponse from(Comment comment) {
            return CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .userId(comment.getUser().getId())
                    .username(comment.getUser().getUsername())
                    .build();

        }
    }

    // TODO: 클래스를 분리해서 정적 팩토리 메서드를 여러개 추가 --> 여러 클래스에서 필요한 정보만 응답 ( forMainStory, forProfile ... )
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