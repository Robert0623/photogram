package com.hwoo.photogram.web.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubscribeResponse {

    private Long id;
    private String username;
    private String profileImageUrl;
    private Integer subscribeState;
    private Integer equalUserState;

    @Builder
    public SubscribeResponse(Long id, String username, String profileImageUrl, Integer subscribeState, Integer equalUserState) {
        this.id = id;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.subscribeState = subscribeState;
        this.equalUserState = equalUserState;
    }
}
