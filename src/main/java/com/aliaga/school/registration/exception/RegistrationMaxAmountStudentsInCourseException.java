package com.aliaga.school.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Course is completed, not more students allowed")
public class RegistrationMaxAmountStudentsInCourseException extends Exception {
    public RegistrationMaxAmountStudentsInCourseException(String message) {
        super(message);
    }
}
