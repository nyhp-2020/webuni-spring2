package hu.webuni.student.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import hu.webuni.student.model.Student;
import hu.webuni.student.repository.StudentRepository;
import hu.webuni.student.service.StudentService;
import hu.webuni.student.ws.SendFreeSemestersMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SendMessageConsumer {
	

	private final StudentRepository studentRepository;

	@JmsListener(destination = "freeSemesters")
	public void onSendMessage(SendFreeSemestersMessage sendMessage) {
		System.out.println(sendMessage);
		long id = sendMessage.getId();
		int freeSemesters = sendMessage.getFreeSemesters();
		Student student = studentRepository.findByIdWithRelationships(id);
		if (student != null) {
			student.setUfsc(freeSemesters);
			studentRepository.save(student);
		}
	}

}
