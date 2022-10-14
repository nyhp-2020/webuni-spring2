package hu.webuni.student.web;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import hu.webuni.student.dto.CourseDto;
import hu.webuni.student.mapper.CourseMapper;
import hu.webuni.student.model.Course;
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
}
