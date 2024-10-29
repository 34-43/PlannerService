package com.sparta.plannerservice.common.exception;

import com.sparta.plannerservice.common.dto.ExceptionResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// 모든 RestController 에서 발생할 수 있는 예외 처리에 대한 관심사를 분리하여 수행하는 Handler 메소드의 모체 클래스

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 알 수 없는 Runtime 예외에 대한 Handler. Internal Server Error(500) 반환
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResDto> handleRuntimeException(RuntimeException ex) {
        return ExceptionResDto.responseWith(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // 사용자가 정의한 예외 (Id Not Found 등) 에 대한 Handler. Not Found(404) 또는 Bad Request(400) 반환
    @ExceptionHandler(FailedRequestException.class)
    public ResponseEntity<ExceptionResDto> handleFailedRequestException(FailedRequestException ex) {
        if (ex.getClass().equals(IdNotFoundException.class)) {
            return ExceptionResDto.responseWith(HttpStatus.NOT_FOUND,ex.getMessage());
        } else {
            return ExceptionResDto.responseWith(HttpStatus.BAD_REQUEST,ex.getMessage());
        }
    }

    // Dto validation 에 의해 발생하는 인자 조건 불일치 예외에 대한 Handler. Bad Request(400) 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
