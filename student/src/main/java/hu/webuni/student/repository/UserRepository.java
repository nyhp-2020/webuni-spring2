package hu.webuni.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.student.model.StudentUser;
import hu.webuni.student.model.UniversityUser;



public interface UserRepository extends JpaRepository<UniversityUser, String>{
	Optional<UniversityUser> findByUsername(String username);
	Optional<UniversityUser> findByFacebookId(String facebookId);
	Optional<UniversityUser> findByGoogleId(String googleId);
	
//	Optional<StudentUser> findByFacebookId(String facebookId);
}
