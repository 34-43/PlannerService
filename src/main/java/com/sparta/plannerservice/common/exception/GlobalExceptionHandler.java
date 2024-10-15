package com.sparta.plannerservice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// 모든 RestController 에서 발생할 수 있는 예외 처리에 대한 관심사를 분리하여 수행하는 Handler 메소드의 모체 클래스

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 알 수 없는 Runtime 예외에 대한 Handler. Internal Server Error(500) 반환
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // 사용자가 정의한 예외 (Id Not Found 등) 에 대한 Handler. Not Found(404) 또는 Bad Request(400) 반환
    @ExceptionHandler(FailedRequestException.class)
    public ResponseEntity<String> handleFailedRequestException(FailedRequestException ex) {
        if (ex.getClass().equals(IdNotFoundException.class)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Dto validation 에 의해 발생하는 인자 조건 불일치 예외에 대한 Handler. Bad Request(400) 반환
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
