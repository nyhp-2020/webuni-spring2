package hu.webuni.student.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.student.model.Course;
import hu.webuni.student.model.Semester;
import hu.webuni.student.model.Semester.SemesterType;
import hu.webuni.student.model.SpecialDay;
import hu.webuni.student.model.Student;
import hu.webuni.student.model.StudentUser;
import hu.webuni.student.model.Teacher;
import hu.webuni.student.model.Timetable;
import hu.webuni.student.repository.CourseRepository;
import hu.webuni.student.repository.SpecialDayRepository;
import hu.webuni.student.repository.StudentRepository;
import hu.webuni.student.repository.TeacherRepository;
import hu.webuni.student.repository.TimetableRepository;
import hu.webuni.student.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InitDbService {
	
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final TimetableRepository timetableRepository;
	private final CourseRepository courseRepository;
	private final SpecialDayRepository specialDayRepository;
	private final JdbcTemplate jdbcTemplate;
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public void addInitData() {

		
		Student student1 = Student.builder().name("Kis Pál").birthdate(LocalDate.of(1981, 4, 12)).semester(1).cid(0).ufsc(0).build();
		Student student2 = Student.builder().name("Ladó Béla").birthdate(LocalDate.of(1992, 3, 15)).semester(2).cid(0).ufsc(0).build();
		Student student3 = Student.builder().name("Szél Kálmám").birthdate(LocalDate.of(1983, 9, 11)).semester(3).cid(0).ufsc(0).build();
		Student student4 = Student.builder().name("Arató Dávid").birthdate(LocalDate.of(1984, 11, 5)).semester(4).cid(0).ufsc(0).build();
		

		
		studentRepository.save(student1);
		studentRepository.save(student2);
		studentRepository.save(student3);
		studentRepository.save(student4);
		
		Teacher teacher1 = Teacher.builder().name("Gál Ferenc").birthdate(LocalDate.of(1950, 10, 21)).build();
		Teacher teacher2 = Teacher.builder().name("Pál Ferenc").birthdate(LocalDate.of(1960, 12, 23)).build();
		Teacher teacher3 = Teacher.builder().name("Hári János").birthdate(LocalDate.of(1965, 1, 31)).build();
		Teacher teacher4 = Teacher.builder().name("Gábor Dénes").birthdate(LocalDate.of(1949, 8, 20)).build();
		

		teacherRepository.save(teacher1);
		teacherRepository.save(teacher2);
		teacherRepository.save(teacher3);
		teacherRepository.save(teacher4);
		

		
//		Timetable timetable1 = new Timetable(0,DayOfWeek.MONDAY,LocalTime.of(8, 0),LocalTime.of(9, 0),null);
//		Timetable timetable2 = new Timetable(0,DayOfWeek.TUESDAY,LocalTime.of(8, 0),LocalTime.of(9, 0),null);
//		Timetable timetable3 = new Timetable(0,DayOfWeek.WEDNESDAY,LocalTime.of(8, 0),LocalTime.of(9, 0),null);
//		Timetable timetable4 = new Timetable(0,DayOfWeek.THURSDAY,LocalTime.of(8, 0),LocalTime.of(9, 0),null);
	
		
		Course course1 = createCourse("Matematika", Arrays.asList(teacher1, teacher4), Arrays.asList(student1, student2), 2022, SemesterType.SPRING);
		Course course2 = createCourse("Fizika", Arrays.asList(teacher1,teacher3), Arrays.asList(student1, student2 ,student3), 2022, SemesterType.SPRING);
		Course course3 = createCourse("Kémia", Arrays.asList(teacher2), Arrays.asList(student4), 2022, SemesterType.SPRING);
		Course course4 = createCourse("Informatika", Arrays.asList(teacher1, teacher3), Arrays.asList(student3, student4), 2022, SemesterType.SPRING);
		
//		Course course1 = new Course(0,"Matematika",Set.copyOf(Arrays.asList(student1, student2)),Set.copyOf(Arrays.asList(teacher1, teacher4)),Set.copyOf(Arrays.asList(timetable1)));
//		Course course2 = new Course(0,"Fizika",Set.copyOf(Arrays.asList(student1, student2, student3)),Set.copyOf(Arrays.asList(teacher1, teacher3)),Set.copyOf(Arrays.asList(timetable2)));
//		Course course3 = new Course(0,"Kémia",Set.copyOf(Arrays.asList(student4)),Set.copyOf(Arrays.asList(teacher2)),Set.copyOf(Arrays.asList(timetable3)));
//		Course course4 = new Course(0,"Informatika",Set.copyOf(Arrays.asList(student3, student4)),Set.copyOf(Arrays.asList(teacher1, teacher3)),Set.copyOf(Arrays.asList(timetable4)));
		
		addNewTimeTable(course1, DayOfWeek.MONDAY, "10:15", "11:45");
		addNewTimeTable(course1, DayOfWeek.WEDNESDAY, "10:15", "11:45");
		addNewTimeTable(course2, DayOfWeek.TUESDAY, "12:15", "13:45");
		addNewTimeTable(course2, DayOfWeek.THURSDAY, "10:15", "11:45");
		addNewTimeTable(course3, DayOfWeek.WEDNESDAY, "08:15", "09:45");
		addNewTimeTable(course4, DayOfWeek.FRIDAY, "08:15", "09:45");
		
		saveSpecialDay("2022-04-18", null);
		saveSpecialDay("2022-04-20", null);
		saveSpecialDay("2022-03-15", null);
		saveSpecialDay("2022-03-14", "2022-03-26");
		
//		timetable1.setCourse(courseRepository.save(course1));
//		timetable2.setCourse(courseRepository.save(course2));
//		timetable3.setCourse(courseRepository.save(course3));
//		timetable4.setCourse(courseRepository.save(course4));
//		
//		timetableRepository.save(timetable1);
//		timetableRepository.save(timetable2);
//		timetableRepository.save(timetable3);
//		timetableRepository.save(timetable4);
		
		System.out.format("Student ids: %d, %d, %d, %d%n",
		student1.getId(), student2.getId(), student3.getId(),student4.getId());
		
		createUsers();
	}
	
	private void saveSpecialDay(String sourceDay, String targetDay) {
		specialDayRepository.save(
				SpecialDay.builder()
				.sourceDay(LocalDate.parse(sourceDay))
				.targetDay(targetDay == null ? null : LocalDate.parse(targetDay))
				.build());
			
	}
	
	
	private Course createCourse(String name, List<Teacher> teachers, List<Student> students, int year, SemesterType semesterType) {
		return courseRepository.save(				
			Course.builder()
			.name(name)
			.teachers(new HashSet<>(teachers))
			.students(new HashSet<>(students))
			.semester(
				Semester.builder()
					.year(year)
					.semesterType(semesterType)
					.build())
			.build());
	}
	
	private void addNewTimeTable(Course course, DayOfWeek dayOfWeek, String startTime, String endTime) {
		course.addTimeTable(timetableRepository.save(
			Timetable.builder()
			.dayOfWeek(dayOfWeek)
			.startTime(LocalTime.parse(startTime))
			.endTime(LocalTime.parse(endTime))
			.build()
			));
	}
	
	@Transactional
	public void deleteDB() {
		specialDayRepository.deleteAllInBatch();
		studentRepository.deleteAllInBatch();
		teacherRepository.deleteAllInBatch();
		timetableRepository.deleteAllInBatch();
		courseRepository.deleteAllInBatch();
	}
	
	@Transactional
	public void deleteAudTables(){
		jdbcTemplate.update("DELETE FROM special_day_aud");
		jdbcTemplate.update("DELETE FROM course_aud");
		jdbcTemplate.update("DELETE FROM student_aud");
		jdbcTemplate.update("DELETE FROM teacher_aud");
		jdbcTemplate.update("DELETE FROM course_students_aud");
		jdbcTemplate.update("DELETE FROM course_teachers_aud");
		jdbcTemplate.update("DELETE FROM timetable_aud");
		jdbcTemplate.update("DELETE FROM revinfo");
	}
	
	@Transactional
	public void createUsers() {
		if(!userRepository.existsById("admin")) {
			userRepository.save(new StudentUser("admin", passwordEncoder.encode("pass"), Set.of("admin", "user")));
		}
		
		if(!userRepository.existsById("user")) {
			userRepository.save(new StudentUser("user", passwordEncoder.encode("pass"), Set.of("user")));
		}
	}

}
