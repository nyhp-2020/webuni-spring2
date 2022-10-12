package hu.webuni.student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

import hu.webuni.student.model.Course;
import hu.webuni.student.model.QCourse;
import hu.webuni.student.model.Student;
import hu.webuni.student.model.Teacher;
import hu.webuni.student.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseService {
	
	private final CourseRepository courseRepository;
	
	public List<Course> findAll(){
		return courseRepository.findAll();
	}
	
	public Optional<Course> findById(long id){
		return courseRepository.findById(null);
	}
	
	public List<Course> findCoursesByExample(Course example) {
		
		long id = example.getId();
		String name = example.getName();
		
//		Set<Teacher> teachers = example.getTeachers();		
//		Set<Student> students = example.getStudents();
		
		ArrayList<Predicate> predicates = new ArrayList<Predicate>();
		
		QCourse course = QCourse.course;
		
		if (id > 0) {
			predicates.add(course.id.eq(id));
		}
		
		if (StringUtils.hasText(name))
			predicates.add(course.name.startsWithIgnoreCase(name));
		

		return Lists.newArrayList(courseRepository.findAll(ExpressionUtils.allOf(predicates)));
		
	}

}
