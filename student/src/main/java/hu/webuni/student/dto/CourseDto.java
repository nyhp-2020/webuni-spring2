package hu.webuni.student.dto;

import java.util.List;

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
	private List<StudentDto> students;
	private List<TeacherDto> teachers;

}
