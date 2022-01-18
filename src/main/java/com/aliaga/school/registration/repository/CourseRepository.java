package com.aliaga.school.registration.repository;

import com.aliaga.school.registration.repository.entity.CourseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<CourseEntity, Long> {

    List<CourseEntity> findByName(String name);

    @Override
    List<CourseEntity> findAll();

    @Query(value = "SELECT c FROM CourseEntity c WHERE c.id NOT IN (SELECT r.course.id FROM RegistrationEntity r) ")
    List<CourseEntity> getCoursesWithoutStudents();
}
