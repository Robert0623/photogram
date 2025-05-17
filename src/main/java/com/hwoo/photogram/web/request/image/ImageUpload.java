package com.hwoo.photogram.web.request.image;

import com.hwoo.photogram.domain.image.Image;
import com.hwoo.photogram.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageUpload {

    private MultipartFile file;
    private String caption;

    public Image toEntity(String imageFileName, User user) {
        return Image.builder()
                .postImageUrl(imageFileName)
                .caption(this.caption)
                .user(user)
                .build();
    }
}