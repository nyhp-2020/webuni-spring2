package hu.webuni.student.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.cache.annotation.Cacheable;
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
import hu.webuni.student.model.HistoryData;
import hu.webuni.student.model.QCourse;
import hu.webuni.student.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseService {
	
	private final CourseRepository courseRepository;
	
	@PersistenceContext //Injection
	private EntityManager em;
	
	@Transactional
	@Cacheable("pagedCoursesWithRelationships")
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
//		return courseRepository.findById(id);
		return Optional.ofNullable(courseRepository.findByIdWithRelationships(id));
	}
	
	@Transactional
	public void delete(long id) {
		courseRepository.deleteById(id);
	}
	
	@Transactional
	public Course save(Course course) {
		return courseRepository.save(course);
	}
	
	@Transactional
	public Course update(Course course) {
		if(courseRepository.existsById(course.getId())) {
			return courseRepository.save(course);
		}
		else
			throw new NoSuchElementException();
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
	
	@Transactional
	@SuppressWarnings({"rawtypes","unchecked"})
	public List<HistoryData<Course>> getCourseHistory(long id){
		List resultList = AuditReaderFactory.get(em)
		.createQuery()
//		.forRevisionsOfEntity(Course.class,true,true) //csak entitások,törölt sorok
		.forRevisionsOfEntity(Course.class,false,true) //revision infok is,törölt sorok
		.add(AuditEntity.property("id").eq(id))
		.getResultList()
		.stream()
		.map(o ->{
			Object[] objArray = (Object[])o;
			DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity)objArray[1];
			Course course = (Course)objArray[0];
			course.getStudents().size();
			course.getTeachers().size();
			return new HistoryData<Course>(
				course,
				(RevisionType)objArray[2],
				revisionEntity.getId(),
				revisionEntity.getRevisionDate()
			);
		}).toList();
		return resultList;
	}
	
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public Course getVersionAt(long id, OffsetDateTime when) {
	public Course getVersionAt(long id, LocalDateTime time) {
		ZoneOffset offset = ZoneOffset.UTC;
		OffsetDateTime when = time.atOffset(offset);
		
		long epochMillis = when.toInstant().toEpochMilli();
		
		List resultList = AuditReaderFactory.get(em)
			.createQuery()			
			.forRevisionsOfEntity(Course.class, true, false)
			.add(AuditEntity.property("id").eq(id))
			.add(AuditEntity.revisionProperty("timestamp").le(epochMillis))
			.addOrder(AuditEntity.revisionProperty("timestamp").desc())
				.setMaxResults(1)
				.getResultList();
		
		if(!resultList.isEmpty()) {
			Course course = (Course) resultList.get(0);
			course.getStudents().size();
			course.getTeachers().size();
			return course;
		}
					
		return null;
	}

}
