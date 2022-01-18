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

    private static final String NOT_FOUND = "Student not found";

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public Student createStudent(Student student) throws StudentServiceException {
        try {
            StudentEntity newStudent = studentRepository.save(studentMapper.toStudentEntity(student));
            return studentMapper.toStudentDTO(newStudent);
        } catch (PersistenceException e) {
            throw new StudentServiceException(e.getMessage());
        }
    }

    public Student getStudentById(long id) throws StudentNotFoundException {
        try {
            Optional<StudentEntity> student = studentRepository.findById(id);
            if (student.isPresent()) {
                return studentMapper.toStudentDTO(student.get());
            } else {
                throw new StudentNotFoundException(NOT_FOUND);
            }
        } catch (StudentNotFoundException e) {
            throw new StudentNotFoundException(e.getMessage());
        }
    }

    public Student updateStudent(long id, Student student) throws StudentNotFoundException, StudentServiceException {
        try {
            Optional<StudentEntity> studentEntity = studentRepository.findById(id);
            if (studentEntity.isPresent()) {
                StudentEntity studentToUpdate = studentMapper.toStudentEntity(student);
                return studentMapper.toStudentDTO(studentRepository.save(studentToUpdate));
            } else {
                throw new StudentNotFoundException(NOT_FOUND);
            }
        } catch (PersistenceException e) {
            throw new StudentServiceException(e.getMessage());
        }
    }

    public void deleteStudent(long id) throws StudentServiceException {
        try {
            Optional<StudentEntity> studentEntity = studentRepository.findById(id);
            studentEntity.ifPresent(studentRepository::delete);
        } catch (PersistenceException e) {
            throw new StudentServiceException(e.getMessage());
        }
    }

}
