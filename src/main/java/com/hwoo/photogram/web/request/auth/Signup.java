package com.hwoo.photogram.web.request.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // form방식은 setter를 사용해서 객체에 데이터를 주입
@NoArgsConstructor
public class Signup {

    private String username;
    private String email;
    private String name;
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
