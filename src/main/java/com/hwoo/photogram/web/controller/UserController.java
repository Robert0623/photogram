package com.hwoo.photogram.web.controller;

import com.hwoo.photogram.config.auth.PrincipalDetails;
import com.hwoo.photogram.web.response.UserResponse;
import com.hwoo.photogram.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public String popular(@PathVariable("id") Long id, Model model) {
        UserResponse response = userService.getProfiles(id);
        model.addAttribute("user", response);

        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        // model.addAttribute("principal", principalDetails.getUser());

        return "user/update";
    }

}