package hu.webuni.student.web;

import java.util.List;

import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public List<CourseDto> searchCourses2(@QuerydslPredicate(root = Course.class) Predicate predicate){
//		return courseMapper.coursesToDtos(courseRepository.findAll(predicate));
		return courseMapper.courseSummariesToDtos(courseRepository.findAll(predicate));
	}
}
