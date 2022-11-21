package hu.webuni.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.student.model.StudentUser;

public interface StudentUserRepository extends JpaRepository<StudentUser, String>{
	Optional<StudentUser> findByFacebookId(String facebookId);
}
