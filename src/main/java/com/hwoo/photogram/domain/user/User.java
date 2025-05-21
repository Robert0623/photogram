package com.hwoo.photogram.domain.user;

import com.hwoo.photogram.domain.image.Image;
import com.hwoo.photogram.web.request.user.UserUpdate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity // db에 테이블을 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 db를 따라간다. (mysql, oracle ...)
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String website; // 웹사이트
    private String bio; // 자기 소개
    private String phone;
    private String gender;

    private String profileImageUrl; // 사진
    private String role; // 권한

    private LocalDateTime createdDate;

    // 나는 연관관계의 주인이 아니다.그러므로 테이블에 컬럼을 만들지마.
    // User를 select할 때 해당 userId로 등록된 image들을 다 가져와.
    // Lazy --> User를 select할 때 해당 userId로 등록된 image들을 가져오지마.
    //          대신 getImages() 함수의 image가 호출될 때 가져와.
    // Eager --> User를 select할 때 해당 userId로 등록된 image들을 전부 join해서 가져와.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    // @JsonIgnoreProperties({"user"})
    private List<Image> images;

    @PrePersist // db에 insert 되기 직전에 실행
    public void createdDate() {
        this.createdDate = LocalDateTime.now();
    }

    @Builder
    public User(String username, String email, String password, String name, String website, String bio, String phone, String gender, String profileImageUrl, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.website = website;
        this.bio = bio;
        this.phone = phone;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public void edit(UserUpdate dto) {
        if (dto.getName() != null && !dto.getName().isBlank()) {
            this.name = dto.getName();
        }

        this.website = dto.getWebsite();
        this.bio = dto.getBio();
        this.phone = dto.getPhone();
        this.gender = dto.getGender();
    }

    public void editPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
}
