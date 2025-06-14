package com.hwoo.photogram.handler;

import com.hwoo.photogram.handler.ex.CustomApiException;
import com.hwoo.photogram.handler.ex.CustomException;
import com.hwoo.photogram.handler.ex.CustomValidationException;
import com.hwoo.photogram.util.Script;
import com.hwoo.photogram.web.enums.CommonResponseCode;
import com.hwoo.photogram.web.exception.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.<Map<String, String>>builder()
//                .code(CommonResponseCode.VALIDATION_FAIL.getCode())
//                .message(e.getMessage())
//                .data(e.getErrorMap())
//                .build());
        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        }

        return Script.back(e.getErrorMap().toString());
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> customApiException(CustomApiException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.<Map<String, String>>builder()
                .code(CommonResponseCode.VALIDATION_FAIL.getCode())
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : e.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.error("유효성 검사 실패", errorMap));
    }

    @ExceptionHandler(CustomException.class)
    public String customException(CustomException e) {
        return Script.back(e.getMessage());
    }
}