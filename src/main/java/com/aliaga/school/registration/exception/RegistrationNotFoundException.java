package com.aliaga.school.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Registration not found")
public class RegistrationNotFoundException extends Exception {
    public RegistrationNotFoundException(String message) {
        super(message);
    }
}
