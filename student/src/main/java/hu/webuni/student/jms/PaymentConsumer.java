package hu.webuni.student.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import hu.webuni.student.finance.dto.PaymentDto;
import hu.webuni.student.service.StudentService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {
	
	private final StudentService studentService;
	
//	@JmsListener(destination = "payments")
	@JmsListener(destination = "payments",containerFactory = "myFactory")
	public void onPaymentMessage(PaymentDto paymentDto) {
		studentService.updateBalance(paymentDto.getStudentId(), paymentDto.getAmount());
	}
}
