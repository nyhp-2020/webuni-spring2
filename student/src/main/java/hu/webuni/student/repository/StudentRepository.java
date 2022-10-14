package hu.webuni.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.student.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{



}
