package hu.webuni.university.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Cacheable
public class Student {

	@Id
	@GeneratedValue
	@ToString.Include
	@EqualsAndHashCode.Include
	private int id;

	@ToString.Include
	private String name;
	
	private LocalDate birthdate;
	private int semester;
	
	@ManyToMany(mappedBy = "students")
	private Set<Course> courses;
}
