package hu.webuni.student.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer{

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//		messages
//		.nullDestMatcher().authenticated()
//		.simpSubscribeDestMatchers("/topic/course/*").hasAuthority("user")
//		.anyMessage().denyAll();
		
		messages.simpSubscribeDestMatchers("/topic/courseChat/{courseId}")
		.access("@courseChatGuard.checkCourseId(authentication, #courseId)");
	}

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}
}
