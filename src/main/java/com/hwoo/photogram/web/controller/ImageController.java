package com.hwoo.photogram.web.controller;

import com.hwoo.photogram.config.auth.PrincipalDetails;
import com.hwoo.photogram.web.request.image.ImageUpload;
import com.hwoo.photogram.web.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "/image"})
    public String story() {
        return "image/story";
    }

    @GetMapping("/image/popular")
    public String popular() {
        return "image/popular";
    }

    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              @ModelAttribute ImageUpload request) {
        imageService.imageUpload(principalDetails.getUser(), request);

        return "redirect:/user/" + principalDetails.getUser().getId();
    }
}