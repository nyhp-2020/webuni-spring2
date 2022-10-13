package hu.webuni.student.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Course {
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	@ToString.Include
	private long id;
	
	@ToString.Include
	private String name;
	
//	@OneToMany(mappedBy = "course")
	@ManyToMany
	private Set<Student> students;
	
//	@OneToMany(mappedBy = "course"
	@ManyToMany
	private Set<Teacher> teachers;

}
