package hu.webuni.student.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import hu.webuni.student.ws.ChatMessage;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	
	private final SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/chat")
	public void send(ChatMessage message) {
		messagingTemplate.convertAndSend("/topic/courseChat/" + message.getCourseId(),
				String.format("%s: %s", message.getSender(), message.getText()));
	}
}
