package com.hwoo.photogram.config.handler;

import com.hwoo.photogram.config.handler.ex.CustomValidationException;
import com.hwoo.photogram.web.enums.CommonResponseCode;
import com.hwoo.photogram.web.exception.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationException(CustomValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.<Map<String, String>>builder()
                .code(CommonResponseCode.VALIDATION_FAIL.getCode())
                .message(e.getMessage())
                .data(e.getErrorMap())
                .build());
//        return Script.back(e.getErrorMap().toString());
    }

}