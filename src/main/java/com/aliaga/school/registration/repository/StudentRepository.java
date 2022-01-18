package com.aliaga.school.registration.repository;

import com.aliaga.school.registration.repository.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

    List<StudentEntity> findByLastname(String lastname);

    @Override
    List<StudentEntity> findAll();
}
