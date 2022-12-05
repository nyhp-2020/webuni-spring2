package hu.webuni.student.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import hu.webuni.student.service.StudentService;
import hu.webuni.student.ws.SendFreeSemestersMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SendMessageConsumer {
	

//	private final StudentRepository studentRepository;
	
	private final StudentService studentService;

//	@JmsListener(destination = "freeSemesters")
	@JmsListener(destination = StudentService.RESPONSE, containerFactory = "educationFactory")
	public void onSendMessage(SendFreeSemestersMessage sendMessage) {
		System.out.println(sendMessage);
		long id = sendMessage.getId();
		int freeSemesters = sendMessage.getFreeSemesters();
		
		studentService.updateSemester(id, freeSemesters);
		
//		Student student = studentRepository.findByIdWithRelationships(id);
//		if (student != null) {
//			student.setUfsc(freeSemesters);
//			studentRepository.save(student);
//		}
	}

}
