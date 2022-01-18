package com.medadata.school.registration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Course {

    private long id;

    @JsonProperty("course-name")
    private String name;

    @JsonProperty("duration-in-days")
    private int duration;
}
