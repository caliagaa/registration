package com.aliaga.school.registration.repository;

import com.aliaga.school.registration.repository.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends CrudRepository<RegistrationEntity, Long> {

    @Override
    List<RegistrationEntity> findAll();

    List<RegistrationEntity> getStudentsByCourseId(long courseId);

    List<RegistrationEntity> getCoursesByStudentId(long studentId);

    @Query(value = "SELECT COUNT(r.student.id) FROM RegistrationEntity r Group by r.course.id Having r.course.id = ?1")
    Integer getStudentCountByCourse(long courseId);

    @Query(value = "SELECT COUNT(r.course.id) FROM RegistrationEntity r Group by r.student.id Having r.student.id = ?1")
    Integer getCoursesCountByStudent(long studentId);
}
