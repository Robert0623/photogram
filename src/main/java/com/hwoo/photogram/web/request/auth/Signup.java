package com.hwoo.photogram.web.request.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
}
