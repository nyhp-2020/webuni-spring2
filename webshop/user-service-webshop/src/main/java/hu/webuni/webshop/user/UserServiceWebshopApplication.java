package hu.webuni.webshop.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.tokenlib.JwtAuthFilter;
import hu.webuni.webshop.user.repository.UserRepository;
import hu.webuni.webshop.user.service.InitDbService;
import lombok.RequiredArgsConstructor;

//scanBasePackageClasses használata, ha más package-ből is használunk osztályokat.
@RequiredArgsConstructor
@SpringBootApplication(scanBasePackageClasses = {JwtAuthFilter.class, UserServiceWebshopApplication.class})
public class UserServiceWebshopApplication implements CommandLineRunner{
	
	private final InitDbService initDbService;
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceWebshopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initDbService.addInitData();
	}

}
