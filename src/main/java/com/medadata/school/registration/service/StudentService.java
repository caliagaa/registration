package com.medadata.school.registration.service;

import com.medadata.school.registration.dto.Student;
import com.medadata.school.registration.exception.StudentNotFoundException;
import com.medadata.school.registration.exception.StudentServiceException;
import com.medadata.school.registration.mapper.StudentMapper;
import com.medadata.school.registration.repository.StudentRepository;
import com.medadata.school.registration.repository.entity.StudentEntity;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public Student createStudent(Student student) throws StudentServiceException {
        StudentEntity result = null;
        try {
            result = studentRepository.save(studentMapper.toStudentEntity(student));
        } catch (PersistenceException e) {
            throw new StudentServiceException(e.getMessage());
        }
        return studentMapper.toStudentDTO(result);
    }

    public Student getStudentById(long id) throws StudentNotFoundException {
        Optional<StudentEntity> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return studentMapper.toStudentDTO(student.get());
        } else {
            throw new StudentNotFoundException("Student not found");
        }
    }


}
