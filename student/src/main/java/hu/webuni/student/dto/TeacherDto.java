package hu.webuni.student.dto;

import java.time.LocalDate;

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
public class TeacherDto {

	private long id;

	private String name;
	private LocalDate birthdate;
//	private CourseDto course;

}
