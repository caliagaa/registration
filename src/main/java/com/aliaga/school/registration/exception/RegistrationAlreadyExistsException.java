package com.aliaga.school.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Registration already exist")
public class RegistrationAlreadyExistsException extends Exception{
    public RegistrationAlreadyExistsException(String message) {
        super(message);
    }
}

