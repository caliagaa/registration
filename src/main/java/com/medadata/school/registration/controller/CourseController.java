package com.medadata.school.registration.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/courses")
public class CourseController {

    @PostMapping
    public void createCourse() {

    }

    @GetMapping
    public void readCourse() {

    }

    @PutMapping
    public void updateCourse() {

    }

    @DeleteMapping
    public void deleteCourse() {

    }
}