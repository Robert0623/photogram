package com.hwoo.photogram.web.controller;

import com.hwoo.photogram.web.request.auth.Signup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class AuthController {

    @GetMapping("/auth/signin")
    public String singinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String singupForm() {
        return "auth/signup";
    }

    // form action --> csrf 토큰
    @PostMapping("/auth/signup")
    public String signup(Signup request) {
        return "auth/signin";
    }
}
