package com.aliaga.school.registration.mapper;

import com.aliaga.school.registration.dto.Course;
import com.aliaga.school.registration.repository.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper( CourseMapper.class );

    CourseEntity toCourseEntity(Course s );

    Course toCourseDTO(CourseEntity s);

    List<Course> toCourseDTO(List<CourseEntity> s);
}
