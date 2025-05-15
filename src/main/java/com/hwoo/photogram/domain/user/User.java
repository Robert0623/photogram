package com.hwoo.photogram.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity // db에 테이블을 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 db를 따라간다. (mysql, oracle ...)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String website; // 웹사이트
    private String bio; // 자기 소개
    private String phone;
    private String gender;

    private String profileImageUrl; // 사진
    private String role; // 권한

    private LocalDateTime createdDate;

    @PrePersist // db에 insert 되기 직전에 실행
    public void createdDate() {
        this.createdDate = LocalDateTime.now();
    }
}
