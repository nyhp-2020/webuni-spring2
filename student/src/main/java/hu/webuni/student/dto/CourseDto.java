package hu.webuni.student.dto;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.OneToMany;

import hu.webuni.student.model.Student;
import hu.webuni.student.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
	
	private long id;
	
	private String name;
	private Set<StudentDto> students;
	private Set<TeacherDto> teachers;

}
