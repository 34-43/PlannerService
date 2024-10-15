package com.sparta.plannerservice.common.exception;

public class FailedRequestException extends RuntimeException {
    public FailedRequestException(String message) {
        super("<Request Failed> " + message);
    }
}
