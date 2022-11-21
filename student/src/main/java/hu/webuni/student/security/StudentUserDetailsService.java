package hu.webuni.student.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.webuni.student.model.StudentUser;
import hu.webuni.student.repository.StudentUserRepository;



@Service
public class StudentUserDetailsService implements UserDetailsService{

	@Autowired
	StudentUserRepository studentUserRepository;
 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		StudentUser studentUser = studentUserRepository.findById(username)
				.orElseThrow(()-> new UsernameNotFoundException(username));
		
		
		return new User(username, studentUser.getPassword(), 
				studentUser.getRoles().stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList()));
	}
}
