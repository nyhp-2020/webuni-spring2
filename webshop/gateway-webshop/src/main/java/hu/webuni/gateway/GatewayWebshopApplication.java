package hu.webuni.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import hu.webuni.tokenlib.JwtAuthFilter;

//@SpringBootApplication
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = {JwtAuthFilter.class, GatewayWebshopApplication.class})
public class GatewayWebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayWebshopApplication.class, args);
	}

}
