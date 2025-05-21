package com.hwoo.photogram.web.request.user;

import com.hwoo.photogram.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserUpdate {

    // TODO: validation 체크 전체 추가 (SIZE, NotBlank)
    @NotBlank(message = "이름 입력해주세요.")
    private String name;

    @Size(min = 4, max = 10, message = "비밀번호는 4 ~ 10글자이어야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private String website;
    private String bio;
    private String phone;
    private String gender;

    @Builder
    public UserUpdate(String name, String password, String website, String bio, String phone, String gender) {
        this.name = name;
        this.password = password;
        this.website = website;
        this.bio = bio;
        this.phone = phone;
        this.gender = gender;
    }

    public UserUpdate withoutPassword() {
        return UserUpdate.builder()
                .name(this.name)
                .website(this.website)
                .bio(this.bio)
                .phone(this.phone)
                .gender(this.gender)
                .build();
    }

    // TODO: 위험한 코드. 수정 필요.
    public User toEntity() {
        return User.builder()
                .name(name) // 이름은 필수값. 기재 안하면 문제 --> validation 체크
                .password(password) // 패스워드는 필수값. 기재 안하면 문제 --> validation 체크
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}