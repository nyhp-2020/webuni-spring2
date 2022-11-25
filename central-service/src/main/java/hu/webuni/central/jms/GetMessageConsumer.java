package hu.webuni.central.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import hu.webuni.central.wmlws.CentralXmlWsImpl;
import hu.webuni.student.ws.GetFreeSemestersMessage;
import hu.webuni.student.ws.SendFreeSemestersMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetMessageConsumer {
	
	private final CentralXmlWsImpl cService;

	private final JmsTemplate jmsTemplate;

	@JmsListener(destination = "getSemesters")
	public void onGetMessage(GetFreeSemestersMessage getMessage) {
		System.out.println(getMessage);
		long id = getMessage.getId();
		long cid = getMessage.getCid();
		String replyTo = getMessage.getReplyTo();
		int freeSemesters = cService.getFreeSemesterCount(cid);
		SendFreeSemestersMessage sendMessage = new SendFreeSemestersMessage();
		sendMessage.setId(id);
		sendMessage.setFreeSemesters(freeSemesters);
		this.jmsTemplate.convertAndSend(replyTo, sendMessage);
	}
}
