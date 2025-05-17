package com.hwoo.photogram.web.api;

import com.hwoo.photogram.web.request.user.UserEdit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserApiController {

    @PatchMapping("/api/user/{userId}")
    public String update(@PathVariable("userId") Long userId, UserEdit userEdit) {
        log.info("userId={}, userEdit={}", userId, userEdit);
        return "ok";
    }

}