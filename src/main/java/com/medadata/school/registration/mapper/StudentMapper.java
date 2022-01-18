package com.medadata.school.registration.mapper;

import com.medadata.school.registration.dto.Student;
import com.medadata.school.registration.repository.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper( StudentMapper.class );

    StudentEntity toStudentEntity(Student s );

    Student toStudentDTO(StudentEntity s);

    List<Student> toStudentDTO(List<StudentEntity> s);
}


