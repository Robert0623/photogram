package com.hwoo.photogram.web.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserProfileResponse {

    private String username;
    private String email;
    private String name;
    private String website; // 웹사이트
    private String bio; // 자기 소개
    private String phone;
    private String gender;
    private String profileImageUrl; // 사진
    private boolean pageOwnerState; // 페이지 소유자인지
    private long imageCount;

    private List<UserImageResponse> images;

    @Builder
    public UserProfileResponse(String username, String email, String name, String website, String bio, String phone, String gender, String profileImageUrl,  boolean pageOwnerState, long imageCount, List<UserImageResponse> images) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.website = website;
        this.bio = bio;
        this.phone = phone;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
        this.pageOwnerState = pageOwnerState;
        this.imageCount = imageCount;
        this.images = images;
    }
}