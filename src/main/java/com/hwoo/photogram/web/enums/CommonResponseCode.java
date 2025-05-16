package com.hwoo.photogram.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum CommonResponseCode {
    SUCCESS(1, "성공"),
    VALIDATION_FAIL(-1, "유효성 검사 실패");

    private final int code;
    private final String defaultMessage;

}