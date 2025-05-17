package com.hwoo.photogram.web.request.user;

import com.hwoo.photogram.domain.user.User;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserEdit {

    private String name;
    private String password;
    private String website;
    private String bio;
    private String phone;
    private String gender;

    @Builder
    public UserEdit(String name, String password, String website, String bio, String phone, String gender) {
        this.name = name;
        this.password = password;
        this.website = website;
        this.bio = bio;
        this.phone = phone;
        this.gender = gender;
    }

    // TODO: 위험한 코드. 수정 필요.
    public User toEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}