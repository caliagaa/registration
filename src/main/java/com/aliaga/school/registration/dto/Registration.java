package com.aliaga.school.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Registration {
    private long id;
    private Student student;
    private List<Course> courses;
}
