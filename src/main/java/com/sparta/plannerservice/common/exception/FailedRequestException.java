package com.sparta.plannerservice.common.exception;

import com.sparta.plannerservice.common.enums.FailedRequest;
import lombok.Getter;

@Getter
public class FailedRequestException extends RuntimeException {
    private FailedRequest failedRequest = FailedRequest.INTERNAL_ERROR;

    public FailedRequestException(FailedRequest failedRequest) {
        super("<Request Failed> " + failedRequest.getMessage());
        this.failedRequest = failedRequest;
    }

    public FailedRequestException(String message) {
        super("<Request Failed> " + message);
    }
}
