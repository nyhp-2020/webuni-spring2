package hu.webuni.student.web;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.student.model.CourseAvgDat;
import hu.webuni.student.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student/reports")
@RequiredArgsConstructor
public class ReportController {
	
	private final CourseRepository courseRepository;
	
	@GetMapping("/averageSemestersPerCourse")
	@Async
	public CompletableFuture<List<CourseAvgDat>> getSemesterReport() {
		System.out.println("ReportController.getSemesterReport called at thread " + Thread.currentThread().getName());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		
		return CompletableFuture.completedFuture(
				courseRepository.findAverageOfSemesterOfStudents()
				);
	}

}
