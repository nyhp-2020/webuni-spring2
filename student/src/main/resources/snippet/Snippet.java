package snippet;

public class Snippet {
	private Student saveNewStudent(String name, LocalDate birthdate, int semester, int eduId, String username, String pass) {
		return studentRepository.save(
				Student.builder()
					.name(name)
					.birthdate(birthdate)
					.semester(semester)
					.eduId(eduId)
					.username(username)
					.password(passwordEncoder.encode(pass))
					.build());
	}
	
	private Teacher saveNewTeacher(String name, LocalDate birthdate, String username, String pass) {
		return teacherRepository.save(
				Teacher.builder()
					.name(name)
					.birthdate(birthdate)
					.username(username)
					.password(passwordEncoder.encode(pass))
					.build());
	}
}

