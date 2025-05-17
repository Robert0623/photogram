package com.hwoo.photogram.web.request.image;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageUpload {

    private MultipartFile file;
    private String caption;

}