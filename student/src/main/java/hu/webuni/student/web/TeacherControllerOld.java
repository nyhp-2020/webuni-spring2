package hu.webuni.student.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.student.api.model.TeacherDto;
import hu.webuni.student.mapper.TeacherMapper;
import hu.webuni.student.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@RestController
@RequestMapping("/api/teachers")
public class TeacherControllerOld {
	
	private final TeacherRepository teacherRepository;

	private final TeacherMapper teacherMapper;
	
	@GetMapping("/{id}")
	public TeacherDto findById(@PathVariable("id") long id) {
		return teacherMapper.teacherToDto(teacherRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

}
