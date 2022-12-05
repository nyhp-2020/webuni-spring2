package hu.webuni.student.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hu.webuni.student.xmlws.StudentXmlWs;
import lombok.RequiredArgsConstructor;



@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {
	
	private final Bus bus;
	private final StudentXmlWs studentXmlWs;
	
	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus,studentXmlWs);
		endpoint.publish("/timetable");
		return endpoint;
	}
	

}
