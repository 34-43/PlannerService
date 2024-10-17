package com.sparta.plannerservice.common.exception;

public class PasswordFailException extends FailedRequestException {
    public PasswordFailException() {
        super("password does not match");
    }
}
