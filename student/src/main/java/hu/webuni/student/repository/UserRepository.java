package hu.webuni.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.student.model.StudentUser;


public interface UserRepository extends JpaRepository<StudentUser, String>{

}
