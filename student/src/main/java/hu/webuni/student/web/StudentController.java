package hu.webuni.student.web;

import java.io.IOException;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.student.api.StudentControllerApi;
import hu.webuni.student.api.model.StudentDto;
import hu.webuni.student.mapper.StudentMapper;
import hu.webuni.student.repository.StudentRepository;
import hu.webuni.student.service.StudentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentControllerApi{

	private final NativeWebRequest nativeWebRequest;
	private final StudentRepository studentRepository;
	private final StudentService studentService;

	private final StudentMapper studentMapper;

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.of(nativeWebRequest);
	}

	@Override
	public ResponseEntity<StudentDto> findById1(Long id) {
		return ResponseEntity.ok(studentMapper.studentToDto(studentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))));
	}

	@Override
	public ResponseEntity<Resource> getProfilePicture(Long id) {
		return ResponseEntity.ok(studentService.getProfilePicture(id));
	}

	@Override
	public ResponseEntity<Void> uploadProfilePicture(Long id, MultipartFile content) {
		try {
			studentService.saveProfilePicture(id, content.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> deleteProfilePicture(Long id) {
		studentService.deleteProfilePicture(id);
		return ResponseEntity.ok().build();
	}

}
