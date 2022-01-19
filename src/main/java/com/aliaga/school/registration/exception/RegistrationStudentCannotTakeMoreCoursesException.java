package com.aliaga.school.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Students can't take more courses")
public class RegistrationStudentCannotTakeMoreCoursesException extends Exception {
    public RegistrationStudentCannotTakeMoreCoursesException(String message) {
        super(message);
    }
}
