package hu.webuni.student.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.student.model.Student;
import hu.webuni.student.repository.StudentRepository;
import hu.webuni.student.ws.GetFreeSemestersMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {
	
	public String GET_TOPIC = "getSemesters";
	public String RESPONSE_TOPIC = "freeSemesters";
	
//	@Value("${student.scheduled.cronparam}")
//	private String cronparam;
	
//	private final SemesterService semesterService;
	
	private final StudentRepository studentRepository;
	
	private final CentralService centralService;
	
	private final JmsTemplate jmsTemplate;
	
	@Value("${student.content.profilePics}")
	private String profilePicsFolder;
	
	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Path.of(profilePicsFolder));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public List<Student> findAll(){
		return studentRepository.findAll();
	}
	
	public Optional<Student> findById(long id){
		return studentRepository.findById(null);
	}
	
	@Transactional
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	
	@Transactional
	public Student update(Student student) {
		if(studentRepository.existsById(student.getId()))
				return studentRepository.save(student);
		else
			throw new NoSuchElementException();
	}
	
	@Transactional
	public void delete(long id) {
		studentRepository.deleteById(id);
	}
	
	
//	@Scheduled(cron = "*/10 * * * * *")
//	@Scheduled(cron = "${student.scheduled.cronparam}")
	public void updateUsedFreeSemesters() {
		System.out.println("updateUsedFreeSemesters called");
		studentRepository.findAll().forEach(s -> {
			updateStudentWithUsedFreeSemesters(s);
		});
	}

	private void updateStudentWithUsedFreeSemesters(Student s) {
//		int usedFreeSemesters = 0;
//		try {
//			usedFreeSemesters = centralService.getNumFreeSemestersForStudent(s.getCid()); //direct call
//
//			//			usedFreeSemesters = semesterService.getUsedFreeSemesters(s.getCid());
////			usedFreeSemesters = semesterService.getUsedFreeSemesters(0);
//		} catch (Exception e) {
////			System.out.println("Exception occured");
//			System.out.println(e.toString());
//		}
//		s.setUfsc(usedFreeSemesters);
//		studentRepository.save(s);
		
		//Sending message instead of direct call
		GetFreeSemestersMessage getMessage = new GetFreeSemestersMessage();
		getMessage.setId(s.getId());
		getMessage.setCid(s.getCid());
//		getMessage.setReplyTo("freeSemesters");
		getMessage.setReplyTo(RESPONSE_TOPIC);
//		this.jmsTemplate.convertAndSend("getSemesters", getMessage);
		this.jmsTemplate.convertAndSend(GET_TOPIC, getMessage);
	}

	private Path getProfilePicPathForStudent(Long id) {
		return Paths.get(profilePicsFolder, id.toString() + ".jpg");
	}
	
	public void saveProfilePicture(Long id, InputStream is) {
		if(!studentRepository.existsById(id))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		try {
			Files.copy(is, getProfilePicPathForStudent(id), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public Resource getProfilePicture(Long studentId) {
		FileSystemResource fileSystemResource = new FileSystemResource(getProfilePicPathForStudent(studentId));
		if(!fileSystemResource.exists())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return fileSystemResource;
	}
	
	public void deleteProfilePicture(Long studentId) {
//		FileSystemResource fileSystemResource = new FileSystemResource(getProfilePicPathForStudent(studentId));
//		if(!fileSystemResource.exists())
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		try {
			Files.delete(getProfilePicPathForStudent(studentId));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Transactional
	public void updateBalance(long studentId, int amount) {
		studentRepository.findById(studentId).ifPresent(s -> s.setBalance(s.getBalance() + amount));
	}
	
	@Transactional
	public void updateSemester(long studentId, int semester) {
		studentRepository.findById(studentId).ifPresent(s -> s.setUfsc(semester));
	}
}
