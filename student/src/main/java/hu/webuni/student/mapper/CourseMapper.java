package hu.webuni.student.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.student.api.model.CourseDto;
import hu.webuni.student.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
	
	Course dtoToCourse(CourseDto courseDto);
	CourseDto courseToDto(Course course);
	
	@Named("summary")
	@Mapping(target = "students", ignore = true)
	@Mapping(target = "teachers", ignore = true)
	CourseDto courseSummaryToDto(Course course);
	
	@IterableMapping(qualifiedByName = "summary")
	List<CourseDto> courseSummariesToDtos(Iterable<Course> findAll);
	
	List<CourseDto> coursesToDtos(List<Course> courses);
	List<CourseDto> coursesToDtos(Iterable<Course> findAll);

}
