package com.aliaga.school.registration.repository;

import com.aliaga.school.registration.repository.entity.RegistrationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegistrationRepository extends CrudRepository<RegistrationEntity, Long> {

    @Override
    List<RegistrationEntity> findAll();

    List<RegistrationEntity> getStudentsByCourseId(long courseId);

    List<RegistrationEntity> getCoursesByStudentId(long studentId);

}
