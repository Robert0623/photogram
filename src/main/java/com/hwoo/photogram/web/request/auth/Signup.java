package com.hwoo.photogram.web.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // form방식은 setter를 사용해서 객체에 데이터를 주입
@NoArgsConstructor
public class Signup {

    // TODO: validation 체크 전체 추가 (SIZE, NotBlank)
    @Size(min = 2, max = 20, message = "유저네임은 4 ~ 20글자이어야 합니다.")
    @NotBlank(message = "유저네임을 입력해주세요.")
    private String username;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Size(min = 4, max = 10, message = "비밀번호는 4 ~ 10글자이어야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    public Signup(String username, String email, String name, String password) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
    }

//    public User toEntity() {
//        return User.builder()
//                .username(this.username)
//                .email(this.email)
//                .name(this.name)
//                .password(this.password)
//                .build();
//    }
}
