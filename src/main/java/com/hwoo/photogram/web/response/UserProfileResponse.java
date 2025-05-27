package com.hwoo.photogram.web.response;

import com.hwoo.photogram.domain.user.User;
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
    private boolean subscribeState;
    private int subscribeCount;

    private List<UserImageResponse> images;

    @Builder
    public UserProfileResponse(String username, String email, String name, String website, String bio, String phone, String gender, String profileImageUrl, boolean pageOwnerState, long imageCount, boolean subscribeState, int subscribeCount, List<UserImageResponse> images) {
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
        this.subscribeState = subscribeState;
        this.subscribeCount = subscribeCount;
        this.images = images;
    }

    @Builder
    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .website(user.getWebsite())
                .bio(user.getBio())
                .phone(user.getPhone())
                .gender(user.getGender())
                .profileImageUrl(user.getProfileImageUrl())
                .imageCount(user.getImages().size())
                .images(user.getImages().stream()
                        .map(UserImageResponse::from)
                        .toList())
                .build();
    }

    public UserProfileResponse withPageOwnerState(boolean pageOwnerState) {
        return UserProfileResponse.builder()
                .username(this.username)
                .email(this.email)
                .name(this.name)
                .website(this.website)
                .bio(this.bio)
                .phone(this.phone)
                .gender(this.gender)
                .profileImageUrl(this.profileImageUrl)
                .imageCount(this.images.size())
                .images(this.images)
                .pageOwnerState(pageOwnerState)
                .build();
    }

    public static UserProfileResponse from(User user, boolean pageOwnerState, boolean subscribeState, int subscribeCount) {
        return UserProfileResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .website(user.getWebsite())
                .bio(user.getBio())
                .phone(user.getPhone())
                .gender(user.getGender())
                .profileImageUrl(user.getProfileImageUrl())
                .imageCount(user.getImages().size())
                .images(user.getImages().stream()
                        .map(UserImageResponse::from)
                        .toList())
                .pageOwnerState(pageOwnerState)
                .subscribeState(subscribeState)
                .subscribeCount(subscribeCount)
                .build();
    }

}