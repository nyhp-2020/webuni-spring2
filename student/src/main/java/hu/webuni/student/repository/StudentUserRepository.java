package hu.webuni.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.student.model.Student;
import hu.webuni.student.model.StudentUser;

public interface StudentUserRepository extends JpaRepository<StudentUser, String>{
	Optional<StudentUser> findByFacebookId(String facebookId);
}
