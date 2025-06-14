package com.hwoo.photogram.domain.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hwoo.photogram.domain.image.Image;
import com.hwoo.photogram.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String content;

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @JoinColumn(name = "imageId")
    @ManyToOne
    private Image image;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Comment(String content, User user, Image image) {
        this.content = content;
        this.user = user;
        this.image = image;
    }
}