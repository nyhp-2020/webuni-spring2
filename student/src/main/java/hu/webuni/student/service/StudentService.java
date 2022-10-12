package hu.webuni.student.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.student.model.Student;
import hu.webuni.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {
	
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

}
