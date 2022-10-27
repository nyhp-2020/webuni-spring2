package hu.webuni.student.mapper;

import org.mapstruct.Mapper;

import hu.webuni.student.api.model.StudentDto;
import hu.webuni.student.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	
	Student dtoToStudent(StudentDto studentDto);
	StudentDto studentToDto(Student student);
}
