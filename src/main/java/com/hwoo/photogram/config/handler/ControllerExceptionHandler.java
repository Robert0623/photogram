package com.hwoo.photogram.config.handler;

import com.hwoo.photogram.config.handler.ex.CustomValidationException;
import com.hwoo.photogram.util.Script;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
//        return CommonResponse.<Map<String, String>>builder()
//                .code(CommonResponseCode.VALIDATION_FAIL.getCode())
//                .message(e.getMessage())
//                .data(e.getErrorMap())
//                .build();
        return Script.back(e.getErrorMap().toString());
    }
}