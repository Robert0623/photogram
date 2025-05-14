package com.hwoo.photogram.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
