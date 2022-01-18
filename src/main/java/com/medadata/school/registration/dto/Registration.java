package com.medadata.school.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Registration {

    private long id;
    private Student student;
    private List<Course> courses;
}
