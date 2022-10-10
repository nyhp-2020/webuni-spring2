package hu.webuni.student.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Course {
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private long id;
	
	private String name;
	
	@ManyToMany
	private Set<Student> students;
	
	@ManyToMany
	private Set<Teacher> teachers;

}
