package com.hwoo.photogram.web.controller;

import com.hwoo.photogram.config.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @GetMapping("/user/{id}")
    public String popular(@PathVariable("id") Integer id) {
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        // model.addAttribute("principal", principalDetails.getUser());

        return "user/update";
    }

}