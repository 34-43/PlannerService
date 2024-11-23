package com.sparta.plannerservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResDto {
    private HttpStatus httpStatus;
    private String errorMessage;

    public static ResponseEntity<ExceptionResDto> responseWith(HttpStatus httpStatus, String errorMessage) {
        ExceptionResDto exceptionResDto = new ExceptionResDto(httpStatus, errorMessage);
        return new ResponseEntity<ExceptionResDto>(exceptionResDto, httpStatus);
    }
}
