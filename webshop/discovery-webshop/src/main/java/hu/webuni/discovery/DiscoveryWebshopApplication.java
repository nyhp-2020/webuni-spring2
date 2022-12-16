package hu.webuni.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryWebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryWebshopApplication.class, args);
	}

}
