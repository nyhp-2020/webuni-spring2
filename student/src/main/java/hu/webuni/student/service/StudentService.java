package hu.webuni.student.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.student.model.Student;
import hu.webuni.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {
	
//	@Value("${student.scheduled.cronparam}")
//	private String cronparam;
	
	private final SemesterService semesterService;
	
	private final StudentRepository studentRepository;
	
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
	@Scheduled(cron = "${student.scheduled.cronparam}")
	public void updateUsedFreeSemesters() {
		System.out.println("updateUsedFreeSemesters called");
		studentRepository.findAll().forEach(s -> {
			updateStudentWithUsedFreeSemesters(s);
		});
	}

	private void updateStudentWithUsedFreeSemesters(Student s) {
		int usedFreeSemesters = 0;
		try {
			usedFreeSemesters = semesterService.getUsedFreeSemesters(s.getCid());
//			usedFreeSemesters = semesterService.getUsedFreeSemesters(0);
		} catch (Exception e) {
			System.out.println("Exception occured");
		}
		s.setUfsc(usedFreeSemesters);
		studentRepository.save(s);	
	}

}
