package hu.webuni.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import hu.webuni.student.model.Course;
import hu.webuni.student.model.QCourse;

public interface CourseRepository extends JpaRepository<Course, Long>,
	JpaSpecificationExecutor<Course>,
	QuerydslPredicateExecutor<Course>,
	QuerydslBinderCustomizer<QCourse>{
	
	@Override
	default void customize(QuerydslBindings bindings, QCourse course) {
		bindings.bind(course.id).first((path, value) -> path.eq(value));
		bindings.bind(course.name).first((path, value) -> path.startsWithIgnoreCase(value));
	}

}
