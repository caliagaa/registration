package com.medadata.school.registration.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "COURSE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COURSE_NAME")
    private String name;

    @Column(name = "COURSE_DURATION")
    private int duration;
}
