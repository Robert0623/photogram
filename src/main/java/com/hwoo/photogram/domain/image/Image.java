package com.hwoo.photogram.domain.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hwoo.photogram.domain.likes.Likes;
import com.hwoo.photogram.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    @Transient // db에 컬럼이 만들어지지 않는다.
    private boolean likeState;

    @Transient
    private int likeCount;

    // TODO: 이미지 좋아요
    // TODO: 이미지 댓글

    @JsonIgnoreProperties({"images"})
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @PrePersist
    public void createdDate() {
        this.createdDate = LocalDateTime.now();
    }

    @Builder
    public Image(String caption, String postImageUrl, LocalDateTime createdDate, List<Likes> likes, User user) {
        this.caption = caption;
        this.postImageUrl = postImageUrl;
        this.createdDate = createdDate;
        this.likes = likes;
        this.user = user;
    }

    public void editLikeState(boolean likeState) {
        this.likeState = likeState;
    }

    public void editLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}