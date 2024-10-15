package com.sparta.plannerservice.common.exception;

public class IdNotFoundException extends FailedRequestException {
    public IdNotFoundException(Class<?> classType, Long id) {
        super(String.format("Entity type \"%s\" with numerical id (%d) not found", classType.getSimpleName(), id));
    }
}
