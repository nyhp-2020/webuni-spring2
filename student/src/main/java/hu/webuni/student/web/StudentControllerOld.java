package hu.webuni.student.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.student.api.model.StudentDto;
import hu.webuni.student.mapper.StudentMapper;
import hu.webuni.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@RestController
@RequestMapping("/api/students")
public class StudentControllerOld {
	
	private final StudentRepository studentRepository;
	
	private final StudentMapper studentMapper;
	
	@GetMapping("/{id}")
	public StudentDto findById(@PathVariable("id") long id) {
		return studentMapper.studentToDto(studentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

}
