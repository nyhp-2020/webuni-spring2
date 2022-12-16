package hu.webuni.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayWebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayWebshopApplication.class, args);
	}

}
