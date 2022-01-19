package com.aliaga.school.registration.repository;

import com.aliaga.school.registration.repository.entity.StudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

    List<StudentEntity> findByFirstnameAndLastname(String firstname, String lastname);

    @Override
    List<StudentEntity> findAll();

    @Query(value = "SELECT c FROM StudentEntity c WHERE c.id NOT IN (SELECT r.student.id FROM RegistrationEntity r) ")
    List<StudentEntity> getStudentsWithoutCourse();
}

