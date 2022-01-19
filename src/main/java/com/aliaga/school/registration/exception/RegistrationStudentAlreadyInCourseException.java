package com.aliaga.school.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Student is already registered in course")
public class RegistrationStudentAlreadyInCourseException extends Exception {
    public RegistrationStudentAlreadyInCourseException(String message) {
        super(message);
    }
}
