package hu.webuni.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.student.model.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable, Long>{

}
