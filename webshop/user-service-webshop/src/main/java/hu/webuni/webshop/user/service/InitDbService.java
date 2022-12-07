package hu.webuni.webshop.user.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.webshop.user.model.WebshopUser;
import hu.webuni.webshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InitDbService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public void addInitData() {
		createUsers();
	}
	
	@Transactional
	public void createUsers() {
		if(!userRepository.existsByUsername("admin")) {
			userRepository.save(new WebshopUser(0L,"admin", passwordEncoder.encode("pass"), null, null, Set.of("admin", "customer")));
		}
		
		if(!userRepository.existsByUsername("user")) {
			userRepository.save(new WebshopUser(0L,"user", passwordEncoder.encode("pass"), null, null, Set.of("customer")));
		}
		
//		if(!studentUserRepository.existsById("user")) {
//			studentUserRepository.save(new StudentUser("user", passwordEncoder.encode("pass"), Set.of("user")));
//		}
	}

}
