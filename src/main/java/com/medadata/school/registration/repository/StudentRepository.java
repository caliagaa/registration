package com.medadata.school.registration.repository;

import com.medadata.school.registration.repository.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

    List<StudentEntity> findByLastname(String lastname);

    @Override
    List<StudentEntity> findAll();
}
