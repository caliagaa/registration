package com.aliaga.school.registration.repository;

import com.aliaga.school.registration.repository.entity.RegistrationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends CrudRepository<RegistrationEntity, Long> {

    @Override
    List<RegistrationEntity> findAll();

    List<RegistrationEntity> getStudentsByCourseId(long courseId);

    List<RegistrationEntity> getCoursesByStudentId(long studentId);
}
