package hu.webuni.student.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.student.dto.CourseDto;
import hu.webuni.student.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
	
	Course dtoToCourse(CourseDto courseDto);
	
	@Mapping(target = "students", ignore = true)
	@Mapping(target = "teachers", ignore = true)
	CourseDto courseToDto(Course course);
	
	List<CourseDto> coursesToDtos(List<Course> courses);
	List<CourseDto> coursesToDtos(Iterable<Course> findAll);

}
