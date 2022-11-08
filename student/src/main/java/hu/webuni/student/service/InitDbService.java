package hu.webuni.student.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.student.model.Course;
import hu.webuni.student.model.Student;
import hu.webuni.student.model.Teacher;
import hu.webuni.student.model.Timetable;
import hu.webuni.student.repository.CourseRepository;
import hu.webuni.student.repository.StudentRepository;
import hu.webuni.student.repository.TeacherRepository;
import hu.webuni.student.repository.TimetableRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InitDbService {
	
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final TimetableRepository timetableRepository;
	private final CourseRepository courseRepository;
	private final JdbcTemplate jdbcTemplate;
	
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
		
		Student student1 = Student.builder().name("Kis Pál").birthdate(LocalDate.of(1981, 4, 12)).semester(1).cid(0).ufsc(0).build();
		Student student2 = Student.builder().name("Ladó Béla").birthdate(LocalDate.of(1992, 3, 15)).semester(2).cid(0).ufsc(0).build();
		Student student3 = Student.builder().name("Szél Kálmám").birthdate(LocalDate.of(1983, 9, 11)).semester(3).cid(0).ufsc(0).build();
		Student student4 = Student.builder().name("Arató Dávid").birthdate(LocalDate.of(1984, 11, 5)).semester(4).cid(0).ufsc(0).build();
		
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
		
		Timetable timetable1 = new Timetable(0,DayOfWeek.MONDAY,LocalTime.of(8, 0),LocalTime.of(9, 0),null);
		Timetable timetable2 = new Timetable(0,DayOfWeek.TUESDAY,LocalTime.of(8, 0),LocalTime.of(9, 0),null);
		Timetable timetable3 = new Timetable(0,DayOfWeek.WEDNESDAY,LocalTime.of(8, 0),LocalTime.of(9, 0),null);
		Timetable timetable4 = new Timetable(0,DayOfWeek.THURSDAY,LocalTime.of(8, 0),LocalTime.of(9, 0),null);
		
		Course course1 = new Course(0,"Matematika",Set.copyOf(Arrays.asList(student1, student2)),Set.copyOf(Arrays.asList(teacher1, teacher4)),Set.copyOf(Arrays.asList(timetable1)));
		Course course2 = new Course(0,"Fizika",Set.copyOf(Arrays.asList(student1, student2, student3)),Set.copyOf(Arrays.asList(teacher1, teacher3)),Set.copyOf(Arrays.asList(timetable2)));
		Course course3 = new Course(0,"Kémia",Set.copyOf(Arrays.asList(student4)),Set.copyOf(Arrays.asList(teacher2)),Set.copyOf(Arrays.asList(timetable3)));
		Course course4 = new Course(0,"Informatika",Set.copyOf(Arrays.asList(student3, student4)),Set.copyOf(Arrays.asList(teacher1, teacher3)),Set.copyOf(Arrays.asList(timetable4)));
		
		timetable1.setCourse(courseRepository.save(course1));
		timetable2.setCourse(courseRepository.save(course2));
		timetable3.setCourse(courseRepository.save(course3));
		timetable4.setCourse(courseRepository.save(course4));
		
		timetableRepository.save(timetable1);
		timetableRepository.save(timetable2);
		timetableRepository.save(timetable3);
		timetableRepository.save(timetable4);
		
	}
	
	@Transactional
	public void deleteDB() {
		studentRepository.deleteAll();
		teacherRepository.deleteAll();
		timetableRepository.deleteAll();
		courseRepository.deleteAll();
	}
	
	@Transactional
	public void deleteAudTables(){
		jdbcTemplate.update("DELETE FROM course_aud");
		jdbcTemplate.update("DELETE FROM student_aud");
		jdbcTemplate.update("DELETE FROM teacher_aud");
		jdbcTemplate.update("DELETE FROM course_students_aud");
		jdbcTemplate.update("DELETE FROM course_teachers_aud");
		jdbcTemplate.update("DELETE FROM timetable_aud");
	}
	


}
