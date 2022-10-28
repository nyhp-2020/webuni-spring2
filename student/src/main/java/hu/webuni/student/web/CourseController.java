package hu.webuni.student.web;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import com.querydsl.core.types.Predicate;

import hu.webuni.student.api.CourseControllerApi;
import hu.webuni.student.api.model.CourseDto;
import hu.webuni.student.api.model.GetAvgOfSemesterOfStudents200ResponseInner;
import hu.webuni.student.api.model.HistoryDataCourseDto;
import hu.webuni.student.mapper.CourseMapper;
import hu.webuni.student.mapper.HistoryDataMapper;
import hu.webuni.student.mapper.ObjectArrayDataMapper;
import hu.webuni.student.model.Course;
import hu.webuni.student.model.HistoryData;
import hu.webuni.student.repository.CourseRepository;
import hu.webuni.student.service.CourseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CourseController implements CourseControllerApi {

	private final NativeWebRequest nativeWebRequest;
	private final CourseService courseService;
	private final CourseMapper courseMapper;
	private final CourseRepository courseRepository;

	private final HistoryDataMapper historyDataMapper;

	private final PageableHandlerMethodArgumentResolver pageableResolver;
	private final QuerydslPredicateArgumentResolver prediacateResolver;
	private final ObjectArrayDataMapper oaDataMapper;

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.of(nativeWebRequest);
	}

	@Override
	public ResponseEntity<CourseDto> createCourse(@Valid CourseDto courseDto) {
		Course course = courseService.save(courseMapper.dtoToCourse(courseDto));
		return ResponseEntity.ok(courseMapper.courseToDto(course));
	}

	@Override
	public ResponseEntity<Void> deleteCourse(Long id) {
		courseService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<CourseDto> getById(Long id) {
		Course course = courseService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return ResponseEntity.ok(courseMapper.courseToDto(course));
	}

	@Override
	public ResponseEntity<List<HistoryDataCourseDto>> getHistoryById(Long id) {
		List<HistoryData<Course>> courses = courseService.getCourseHistory(id);

		List<HistoryDataCourseDto> courseDtosWithHistory = new ArrayList<>();

		courses.forEach(hd -> {
			courseDtosWithHistory.add(historyDataMapper.courseHistoryDataToDto(hd));
		});

		return ResponseEntity.ok(courseDtosWithHistory);
	}

	@Override
	public ResponseEntity<CourseDto> modifyCourse(Long id, @Valid CourseDto courseDto) {
		Course course = courseMapper.dtoToCourse(courseDto);
		course.setId(id);
		try {
			CourseDto savedCourseDto = courseMapper.courseToDto(courseService.update(course));

			return ResponseEntity.ok(savedCourseDto);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<List<CourseDto>> searchCourses(@Valid CourseDto example) {
		return ResponseEntity
				.ok(courseMapper.coursesToDtos(courseService.findCoursesByExample(courseMapper.dtoToCourse(example))));
	}

	public void configPageable(@SortDefault("id") Pageable pageable) {
	}

	private Pageable createPageable(String pageableConfigurerMethodName) {
		Method method;
		try {
			method = this.getClass().getMethod(pageableConfigurerMethodName, Pageable.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		MethodParameter methodParameter = new MethodParameter(method, 0);
		ModelAndViewContainer mavContainer = null;
		WebDataBinderFactory binderFactory = null;
		Pageable pageable = pageableResolver.resolveArgument(methodParameter, mavContainer, nativeWebRequest,
				binderFactory);
		return pageable;
	}

	public void configurePredicate(@QuerydslPredicate(root = Course.class) Predicate predicate) {
	}

	private Predicate createPredicate(String configMethodName) {
		Method method;
		try {
			method = this.getClass().getMethod(configMethodName, Predicate.class);
			MethodParameter methodParameter = new MethodParameter(method, 0);
			ModelAndViewContainer mavContainer = null;
			WebDataBinderFactory binderFactory = null;
			return (Predicate) prediacateResolver.resolveArgument(methodParameter, mavContainer, nativeWebRequest,
					binderFactory);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public ResponseEntity<List<CourseDto>> searchCourses2(@Valid Boolean full, @Valid Integer page, @Valid Integer size,
			@Valid String sort, @Valid Long id, @Valid String name, @Valid Long studentsId, @Valid String teachersName,
			@Valid List<Object> studentsSemester) {

		boolean isFull = full == null ? false : full;
		boolean isSummaryNeeded = !isFull;

		Pageable pageable = createPageable("configPageable");

		Predicate predicate = createPredicate("configurePredicate");

		Iterable<Course> result = isSummaryNeeded ? courseRepository.findAll(predicate, pageable)
				: courseService.searchCourses(predicate, pageable);

		if (isSummaryNeeded) {
			return ResponseEntity.ok(courseMapper.courseSummariesToDtos(result));
		} else
			return ResponseEntity.ok(courseMapper.coursesToDtos(result));

	}

	@Override
	public ResponseEntity<List<GetAvgOfSemesterOfStudents200ResponseInner>> getAvgOfSemesterOfStudents() {
		List<GetAvgOfSemesterOfStudents200ResponseInner> results = oaDataMapper.objsToInners(courseRepository.findAverageOfSemesterOfStudents());
		return ResponseEntity.ok(results);
	}

	@Override
	public ResponseEntity<CourseDto> getVersionAt(Long id, @Valid LocalDateTime at) {
		return ResponseEntity.ok(
				courseMapper.courseToDto(
					courseService.getVersionAt(id, at)
				)
			);
	}

}
