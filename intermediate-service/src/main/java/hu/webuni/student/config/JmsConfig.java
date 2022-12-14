package hu.webuni.student.config;

import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class JmsConfig {
	
//	@Bean
//	public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
//		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//		converter.setObjectMapper(objectMapper);
//		converter.setTargetType(MessageType.TEXT);
//		converter.setTypeIdPropertyName("_type");
//		return converter;
//	}
	
	@Bean //server side
	public BrokerService broker() throws Exception {
		BrokerService brokerService = new BrokerService();
		brokerService.addConnector("tcp://localhost:8888");
//		brokerService.setPersistent(false); //default is true
		return brokerService;
	}

}
