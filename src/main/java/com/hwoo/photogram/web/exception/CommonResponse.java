package com.hwoo.photogram.web.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommonResponse<T> {

    private int code; // 1(성공), -1(실해)
    private String message;
    private T data;

    @Builder
    public CommonResponse (int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}