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
import hu.webuni.student.repository.StudentUserRepository;
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
	private final StudentUserRepository studentUserRepository;
	private final PasswordEncoder passwordEncoder;
	
	private final StudentService studentService;
	
	@Transactional
	public void addInitData() {

		Student student1 = saveNewStudent("student1", LocalDate.of(2000, 10, 10), 1, 111, "student1", "pass");
		Student student2 = saveNewStudent("student2", LocalDate.of(2000, 10, 10), 2, 222, "student2", "pass");
		Student student3 = saveNewStudent("student3", LocalDate.of(2000, 10, 10), 3, 333, "student3", "pass");

		Teacher teacher1 = saveNewTeacher("teacher1", LocalDate.of(2000, 10, 10), "teacher1", "pass");
		Teacher teacher2 = saveNewTeacher("teacher2", LocalDate.of(2000, 10, 10), "teacher2", "pass");
		Teacher teacher3 = saveNewTeacher("teacher3", LocalDate.of(2000, 10, 10), "teacher3", "pass");
		
		Course course1 = createCourse("course1", Arrays.asList(teacher1, teacher2), Arrays.asList(student1, student2, student3), 2022, SemesterType.SPRING);
		Course course2 = createCourse("course2", Arrays.asList(teacher2), Arrays.asList(student1, student3), 2022, SemesterType.SPRING);
		Course course3 = createCourse("course3", Arrays.asList(teacher1, teacher3), Arrays.asList(student2, student3), 2022, SemesterType.SPRING);

		
		
		
		
		
		addNewTimeTable(course1, DayOfWeek.MONDAY, "10:15", "11:45");
		addNewTimeTable(course1, DayOfWeek.WEDNESDAY, "10:15", "11:45");
		addNewTimeTable(course2, DayOfWeek.TUESDAY, "12:15", "13:45");
		addNewTimeTable(course2, DayOfWeek.THURSDAY, "10:15", "11:45");
		addNewTimeTable(course3, DayOfWeek.WEDNESDAY, "08:15", "09:45");
		addNewTimeTable(course3, DayOfWeek.FRIDAY, "08:15", "09:45");
		
		saveSpecialDay("2022-04-18", null);
		saveSpecialDay("2022-04-20", null);
		saveSpecialDay("2022-03-15", null);
		saveSpecialDay("2022-03-14", "2022-03-26");
		
		
		System.out.format("Student ids: %d, %d, %d%n",
		student1.getId(), student2.getId(), student3.getId());
		
		createUsers();
		
		studentService.updateUsedFreeSemesters();
	}
	
	private Student saveNewStudent(String name, LocalDate birthdate, int semester, int cid, String username, String pass) {
		return studentRepository.save(
				Student.builder()
					.name(name)
					.birthdate(birthdate)
					.semester(semester)
					.cid(cid)
					.username(username)
					.password(passwordEncoder.encode(pass))
					.build());
	}
	
	private Teacher saveNewTeacher(String name, LocalDate birthdate, String username, String pass) {
		return teacherRepository.save(
				Teacher.builder()
					.name(name)
					.birthdate(birthdate)
					.username(username)
					.password(passwordEncoder.encode(pass))
					.build());
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
		jdbcTemplate.update("DELETE FROM university_user_aud");
		jdbcTemplate.update("DELETE FROM revinfo");
	}
	
	@Transactional
	public void createUsers() {
		if(!studentUserRepository.existsById("admin")) {
			studentUserRepository.save(new StudentUser("admin", passwordEncoder.encode("pass"), Set.of("admin", "user")));
		}
		
		if(!studentUserRepository.existsById("user")) {
			studentUserRepository.save(new StudentUser("user", passwordEncoder.encode("pass"), Set.of("user")));
		}
	}

}
