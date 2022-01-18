package com.medadata.school.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends Exception{
    public CourseNotFoundException(String message) {
        super(message);
    }
}

