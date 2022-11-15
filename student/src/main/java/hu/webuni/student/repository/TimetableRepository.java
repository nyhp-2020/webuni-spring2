package hu.webuni.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.student.model.Semester.SemesterType;
import hu.webuni.student.model.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable, Long>{
	
	@Query("SELECT t FROM Timetable t WHERE t.course IN ("
			+	"SELECT c FROM Course c JOIN c.students s "
			+ 	"WHERE s.id=:studentId AND c.semester.year = :year AND c.semester.semesterType = :semesterType"
			+ ")")
	List<Timetable> findByStudentAndSemester(long studentId, int year, SemesterType semesterType);

	@Query("SELECT t FROM Timetable t WHERE t.course IN ("
			+	"SELECT c FROM Course c JOIN c.teachers tr "
			+ 	"WHERE tr.id=:teacherId AND c.semester.year = :year AND c.semester.semesterType = :semesterType"
			+ ")")
	List<Timetable> findByTeacherAndSemester(long teacherId, int year, SemesterType semesterType);

}
