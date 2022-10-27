package hu.webuni.student.mapper;

import org.mapstruct.Mapper;

import hu.webuni.student.api.model.TeacherDto;
import hu.webuni.student.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
	
	Teacher dtoToTeacher(TeacherDto teacherDto);
	TeacherDto teacherToDto(Teacher teacher);
}
