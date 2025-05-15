package com.hwoo.photogram.web.controller;

import com.hwoo.photogram.web.request.auth.Signup;
import com.hwoo.photogram.web.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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
    public String signup(@Valid Signup request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info(">>> {}", error.getDefaultMessage());
            }
        }

        authService.signup(request);
        return "auth/signin";
    }
}
