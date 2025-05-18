package com.hwoo.photogram.domain.image;

import com.hwoo.photogram.domain.user.User;
import com.hwoo.photogram.web.response.UserProfileImageResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption;
    private String postImageUrl; // 사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장. DB에 저장된 경로를 insert
    private LocalDateTime createdDate;

    // TODO: 이미지 좋아요
    // TODO: 이미지 댓글

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @PrePersist
    public void createdDate() {
        this.createdDate = LocalDateTime.now();
    }

    @Builder
    public Image(String caption, String postImageUrl, User user) {
        this.caption = caption;
        this.postImageUrl = postImageUrl;
        this.user = user;
    }

    public UserProfileImageResponse toUserProfileImageResponse() {
        return UserProfileImageResponse.builder()
                .id(this.id)
                .caption(this.caption)
                .postImageUrl(this.postImageUrl)
                .build();
    }
}