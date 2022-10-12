package hu.webuni.student.service;

import org.springframework.stereotype.Service;

import hu.webuni.student.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeacherService {
	
	private final TeacherRepository teacherRepository;

}
