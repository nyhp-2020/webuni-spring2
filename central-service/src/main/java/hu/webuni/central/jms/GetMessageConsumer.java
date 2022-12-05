package hu.webuni.central.jms;

import javax.jms.Topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.Message;
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
	public void onGetMessage(Message<GetFreeSemestersMessage> message) {
		long id = message.getPayload().getId();
		long cid = message.getPayload().getCid();
		int freeSemesters = cService.getFreeSemesterCount(cid);
		
		SendFreeSemestersMessage sendMessage = new SendFreeSemestersMessage();
		sendMessage.setId(id);
		sendMessage.setFreeSemesters(freeSemesters);
		
		jmsTemplate.convertAndSend(
			(Topic)message.getHeaders().get(JmsHeaders.REPLY_TO),	
			sendMessage);
	}

//	@JmsListener(destination = "getSemesters")
//	public void onGetMessage(GetFreeSemestersMessage getMessage) {
//		System.out.println(getMessage);
//		long id = getMessage.getId();
//		long cid = getMessage.getCid();
//		String replyTo = getMessage.getReplyTo();
//		int freeSemesters = cService.getFreeSemesterCount(cid);
//		SendFreeSemestersMessage sendMessage = new SendFreeSemestersMessage();
//		sendMessage.setId(id);
//		sendMessage.setFreeSemesters(freeSemesters);
//		this.jmsTemplate.convertAndSend(replyTo, sendMessage);
//	}
}
