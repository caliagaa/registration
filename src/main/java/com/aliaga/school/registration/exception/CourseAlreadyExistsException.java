package com.aliaga.school.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Course already exist")
public class CourseAlreadyExistsException extends Exception{
    public CourseAlreadyExistsException(String message) {
        super(message);
    }
}

