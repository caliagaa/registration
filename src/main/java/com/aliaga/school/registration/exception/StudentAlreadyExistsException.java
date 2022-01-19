package com.aliaga.school.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Student already exist")
public class StudentAlreadyExistsException extends Exception{
    public StudentAlreadyExistsException(String message) {
        super(message);
    }
}

