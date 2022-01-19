package com.aliaga.school.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An error has occurred, please contact administrator")
public class RegistrationServiceException extends Exception {
    public RegistrationServiceException(String message) {
        super(message);
    }
}
