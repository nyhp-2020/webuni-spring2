package hu.webuni.student.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.student.model.Course;
import hu.webuni.student.model.Student;
import hu.webuni.student.model.Teacher;
import hu.webuni.student.repository.CourseRepository;
import hu.webuni.student.repository.StudentRepository;
import hu.webuni.student.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InitDbService {
	
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final CourseRepository courseRepository;
	
	@Transactional
	public void addInitData() {
//		Course course1 = Course.builder().name("Matematika").build();
//		Course course2 = Course.builder().name("Fizika").build();
//		Course course3 = Course.builder().name("Kémia").build();
//		Course course4 = Course.builder().name("Informatika").build();
//		
//		course1 = courseRepository.save(course1);
//		course2 = courseRepository.save(course2);
//		course3 = courseRepository.save(course3);
//		course4 = courseRepository.save(course4);
		
		Student student1 = Student.builder().name("Kis Pál").birthdate(LocalDate.of(1981, 4, 12)).semester(1).build();
		Student student2 = Student.builder().name("Ladó Béla").birthdate(LocalDate.of(1992, 3, 15)).semester(2).build();
		Student student3 = Student.builder().name("Szél Kálmám").birthdate(LocalDate.of(1983, 9, 11)).semester(3).build();
		Student student4 = Student.builder().name("Arató Dávid").birthdate(LocalDate.of(1984, 11, 5)).semester(4).build();
		
//		student1.setCourse(course1);
//		student2.setCourse(course1);
//		student3.setCourse(course2);
//		student4.setCourse(course2);
		
		studentRepository.save(student1);
		studentRepository.save(student2);
		studentRepository.save(student3);
		studentRepository.save(student4);
		
		Teacher teacher1 = Teacher.builder().name("Gál Ferenc").birthdate(LocalDate.of(1950, 10, 21)).build();
		Teacher teacher2 = Teacher.builder().name("Pál Ferenc").birthdate(LocalDate.of(1960, 12, 23)).build();
		Teacher teacher3 = Teacher.builder().name("Hári János").birthdate(LocalDate.of(1965, 1, 31)).build();
		Teacher teacher4 = Teacher.builder().name("Gábor Dénes").birthdate(LocalDate.of(1949, 8, 20)).build();
		
//		teacher1.setCourse(course2);
//		teacher2.setCourse(course2);
//		teacher3.setCourse(course1);
//		teacher4.setCourse(course1);
		
		teacherRepository.save(teacher1);
		teacherRepository.save(teacher2);
		teacherRepository.save(teacher3);
		teacherRepository.save(teacher4);
		
//		Course course1 = Course.builder()
//				.name("Matematika")
//				.students(Set.copyOf(Arrays.asList(student1, student2)))
//				.teachers(Set.copyOf(Arrays.asList(teacher1, teacher4))).build();
		
		Course course1 = new Course(0,"Matematika",Set.copyOf(Arrays.asList(student1, student2)),Set.copyOf(Arrays.asList(teacher1, teacher4)));
		Course course2 = new Course(0,"Fizika",Set.copyOf(Arrays.asList(student1, student2, student3)),Set.copyOf(Arrays.asList(teacher1, teacher3)));
		Course course3 = new Course(0,"Kémia",Set.copyOf(Arrays.asList(student4)),Set.copyOf(Arrays.asList(teacher2)));
		Course course4 = new Course(0,"Informatika",Set.copyOf(Arrays.asList(student3, student4)),Set.copyOf(Arrays.asList(teacher1, teacher3)));
		
		courseRepository.save(course1);
		courseRepository.save(course2);
		courseRepository.save(course3);
		courseRepository.save(course4);
		
	}
	
	@Transactional
	public void deleteDB() {
		courseRepository.deleteAll();
		studentRepository.deleteAll();
		teacherRepository.deleteAll();
	}	
	


}
