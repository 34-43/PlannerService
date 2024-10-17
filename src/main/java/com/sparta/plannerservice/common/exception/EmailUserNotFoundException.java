package com.sparta.plannerservice.common.exception;

public class EmailUserNotFoundException extends FailedRequestException {
    public EmailUserNotFoundException() {
        super("User with requested Email not found");
    }
}
