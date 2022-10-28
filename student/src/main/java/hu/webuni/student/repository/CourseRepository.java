package hu.webuni.student.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import hu.webuni.student.model.Course;
import hu.webuni.student.model.CourseAvgDat;
import hu.webuni.student.model.QCourse;

public interface CourseRepository extends
	JpaRepository<Course, Long>,
//	JpaSpecificationExecutor<Course>,
	QuerydslPredicateExecutor<Course>,
	QuerydslBinderCustomizer<QCourse>,
	QueryDslWithEntityGraphRepository<Course,Long>{
	
	@Override
	default void customize(QuerydslBindings bindings, QCourse course) {
//		bindings.bind(course.id).first((path, value) -> path.eq(value));
		bindings.bind(course.name).first((path, value) -> path.startsWithIgnoreCase(value));
		bindings.bind(course.teachers.any().name).first((path, value) -> path.startsWithIgnoreCase(value));
//		bindings.bind(course.students.any().id).first((path, value) -> path.eq(value));
		bindings.bind(course.students.any().semester).all((path, values) -> {
			if(values.size() != 2)
				return Optional.empty();
			
			Iterator<? extends Integer> iterator = values.iterator();
			Integer from = iterator.next();
			Integer to = iterator.next();
			
			return Optional.of(path.between(from, to));
		});
	}

	@EntityGraph(attributePaths = {"students","teachers"})
	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findByIdWithRelationships(long id);
	
	@EntityGraph(attributePaths = {"students"})
	@Query("SELECT c FROM Course c")
	List<Course>findAllWithStudents();
	
	@Query("SELECT c.id, AVG(s.semester) FROM Course c LEFT JOIN c.students s GROUP BY c")
	List<CourseAvgDat>findAverageOfSemesterOfStudents();


}
