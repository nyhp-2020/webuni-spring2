package hu.webuni.student.web;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.student.api.TeacherControllerApi;
import hu.webuni.student.api.model.TeacherDto;
import hu.webuni.student.mapper.CourseMapper;
import hu.webuni.student.mapper.HistoryDataMapper;
import hu.webuni.student.mapper.TeacherMapper;
import hu.webuni.student.repository.CourseRepository;
import hu.webuni.student.repository.TeacherRepository;
import hu.webuni.student.service.CourseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TeacherController implements TeacherControllerApi{
	
	private final NativeWebRequest nativeWebRequest;
	private final TeacherRepository teacherRepository;
	
	private final TeacherMapper teacherMapper;

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.of(nativeWebRequest);
	}

	@Override
	public ResponseEntity<TeacherDto> findById(Long id) {
		return ResponseEntity.ok(teacherMapper.teacherToDto(teacherRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))));
	}

	

}
