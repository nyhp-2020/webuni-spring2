package hu.webuni.central.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hu.webuni.central.wmlws.CentralXmlWs;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {
	
	private final Bus bus;
	private final CentralXmlWs centralXmlWs;
	
	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, centralXmlWs);
		endpoint.publish("/central");
		return endpoint;
	}

}
