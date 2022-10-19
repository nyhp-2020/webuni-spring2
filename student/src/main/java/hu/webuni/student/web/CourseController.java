package hu.webuni.student.web;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.querydsl.core.types.Predicate;

import hu.webuni.student.dto.CourseDto;
import hu.webuni.student.mapper.CourseMapper;
import hu.webuni.student.model.Course;
import hu.webuni.student.model.HistoryData;
import hu.webuni.student.repository.CourseRepository;
import hu.webuni.student.service.CourseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class CourseController {
	
	private final CourseService courseService;
	private final CourseMapper courseMapper;
	private final CourseRepository courseRepository;

	@PostMapping("/search")
	public List<CourseDto> searchCourses(@RequestBody CourseDto example){
		return courseMapper.coursesToDtos(courseService.findCoursesByExample(courseMapper.dtoToCourse(example)));
	}
	
	@GetMapping("/search")
	public List<CourseDto> searchCourses2(@QuerydslPredicate(root = Course.class) Predicate predicate,@RequestParam Optional<Boolean> full,@SortDefault("id") Pageable pageable){
//		Iterable<Course> result = courseRepository.findAll(predicate);
		boolean isSummaryNeeded = full.isEmpty() || !full.get();
		Iterable<Course> result = isSummaryNeeded ? courseRepository.findAll(predicate, pageable) : courseService.searchCourses(predicate,pageable);

		if(isSummaryNeeded) {
			return courseMapper.courseSummariesToDtos(result);
		} else
			return courseMapper.coursesToDtos(result);
	}
	
	@GetMapping("/{id}")
	public CourseDto getById(@PathVariable long id) {
		Course course = courseService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return courseMapper.courseSummaryToDto(course);
	}
	
	@GetMapping("/{id}/history")
	public List<HistoryData<CourseDto>> getHistoryById(@PathVariable long id) {
		List<HistoryData<Course>> courses = courseService.getCourseHistory(id);
		
		List<HistoryData<CourseDto>> courseDtosWithHistory = new ArrayList<>();
		
		courses.forEach(hd -> {
			courseDtosWithHistory.add(new HistoryData<>(
//					courseMapper.courseSummaryToDto(hd.getData()),
					courseMapper.courseToDto(hd.getData()),
					hd.getRevType(),
					hd.getRevision(),
					hd.getDate()
					));
		});
		
		return courseDtosWithHistory;
//		return courseMapper.courseSummariesToDtos(courses);
	}
	
	@PostMapping
	public CourseDto createCourse(@RequestBody CourseDto courseDto) {
		Course course = courseService.save(courseMapper.dtoToCourse(courseDto));
		return courseMapper.courseToDto(course);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CourseDto> modifyCourse(@PathVariable long id, @RequestBody CourseDto courseDto) {
		Course course = courseMapper.dtoToCourse(courseDto);
		course.setId(id);
		try {
			CourseDto savedCourseDto = courseMapper.courseToDto(courseService.update(course));

			return ResponseEntity.ok(savedCourseDto);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteCourse(@PathVariable long id) {
		courseService.delete(id);
	}
	
}
