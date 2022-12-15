package hu.webuni.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigWebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigWebshopApplication.class, args);
	}

}
