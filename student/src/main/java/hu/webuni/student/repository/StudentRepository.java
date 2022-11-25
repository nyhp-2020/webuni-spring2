package hu.webuni.student.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.student.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

	@EntityGraph(attributePaths = {"courses"})
	@Query("SELECT s FROM Student s WHERE s.id = :id")
	Student findByIdWithRelationships(long id);

}
