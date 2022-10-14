package hu.webuni.student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import hu.webuni.student.model.Course;
import hu.webuni.student.model.QCourse;
import hu.webuni.student.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseService {
	
	private final CourseRepository courseRepository;
	
	@Transactional
	public List<Course> searchCourses(Predicate predicate,Pageable pageable){
//		List<Course> courses = courseRepository.findAll(predicate,"Course.students",EntityGraphType.LOAD);
//		courses = courseRepository.findAll(QCourse.course.in(courses),"Course.teachers",EntityGraphType.LOAD);
		
		
		Page<Course> coursePage = courseRepository.findAll(predicate, pageable);
		BooleanExpression inPredicate = QCourse.course.in(coursePage.getContent());
		List<Course> courses = courseRepository.findAll(inPredicate,"Course.students",EntityGraphType.LOAD, Sort.unsorted());
		courses = courseRepository.findAll(inPredicate,"Course.teachers",EntityGraphType.LOAD,pageable.getSort());
		return courses;
	}
	
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
