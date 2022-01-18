package com.aliaga.school.registration.repository;

import com.aliaga.school.registration.repository.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Long> {

    List<CourseEntity> findByName(String name);

    @Override
    List<CourseEntity> findAll();
}
