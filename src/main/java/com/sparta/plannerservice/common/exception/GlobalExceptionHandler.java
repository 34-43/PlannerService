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

    // 사용자가 정의한 예외에 대한 Handler. 열거형에 정의된 상태 코드를 사용하여 반환
    @ExceptionHandler(FailedRequestException.class)
    public ResponseEntity<ExceptionResDto> handleFailedRequestException(FailedRequestException ex) {
        return ExceptionResDto.responseWith(ex.getFailedRequest().getStatus(),ex.getMessage());
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
