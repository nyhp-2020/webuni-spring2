package hu.webuni.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.student.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{

}
