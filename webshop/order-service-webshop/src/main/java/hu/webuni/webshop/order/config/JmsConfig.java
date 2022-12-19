package hu.webuni.webshop.order.config;

import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfig {
	
	@Bean //server side
	public BrokerService broker() throws Exception {
		BrokerService brokerService = new BrokerService();
		brokerService.addConnector("tcp://localhost:9999");
//		brokerService.setPersistent(false); //default is true
		return brokerService;
	}

}
