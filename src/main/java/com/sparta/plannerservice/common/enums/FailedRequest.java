package com.sparta.plannerservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FailedRequest {

    // User
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email already exists"),

    // Auth
    EMAIL_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Cannot login with unexisting email"),
    PASSWORD_INCORRECT(HttpStatus.UNAUTHORIZED, "Password incorrect"),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Token not found"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Token invalid"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Token expired"),

    // Admin
    ADMIN_FORBIDDEN(HttpStatus.FORBIDDEN, "You should be authorized as Admin to perform this action"),

    // Common
    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "Id not found"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    ;
    private final HttpStatus status;
    private final String message;
}
