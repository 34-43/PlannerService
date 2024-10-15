package com.sparta.plannerservice.common.exception;

import java.util.UUID;

public class IdNotFoundException extends FailedRequestException {
    public IdNotFoundException(Class<?> classType, UUID id) {
        super(String.format("Entity type \"%s\" with id (%s) not found", classType.getSimpleName(), id.toString()));
    }
}
