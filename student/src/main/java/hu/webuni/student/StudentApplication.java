package hu.webuni.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.student.service.InitDbService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class StudentApplication implements CommandLineRunner{
	
	private final InitDbService initDbService;

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		initDbService.deleteDB();
//		initDbService.addInitData();
		
	}

}
