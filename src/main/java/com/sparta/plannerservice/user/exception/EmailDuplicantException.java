package com.sparta.plannerservice.user.exception;

import com.sparta.plannerservice.common.exception.FailedRequestException;

public class EmailDuplicantException extends FailedRequestException {
    public EmailDuplicantException(String email) {
        super(String.format("email address '%s' is already on use",email));
    }
}
