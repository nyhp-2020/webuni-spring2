package hu.webuni.webshop.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;

import hu.webuni.tokenlib.JwtAuthFilter;
import hu.webuni.webshop.user.service.InitDbService;
import lombok.RequiredArgsConstructor;


//@SpringBootApplication
//@ComponentScan ({"org.springframework.security.core.userdetails","hu.webuni.webshop.user.service","hu.webuni.tokenlib"})
@RequiredArgsConstructor
@SpringBootApplication(scanBasePackageClasses = {JwtAuthFilter.class, AuthenticationManager.class, UserServiceWebshopApplication.class})
public class UserServiceWebshopApplication implements CommandLineRunner{
	
	private final InitDbService initDbService;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceWebshopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initDbService.addInitData();
	}

}
