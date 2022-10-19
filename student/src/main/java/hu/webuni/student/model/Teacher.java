package hu.webuni.student.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Cacheable
@Audited
public class Teacher {
	
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	@ToString.Include
	private long id;
	
	@ToString.Include
	private String name;
	private LocalDate birthdate;
	
//	@ManyToOne	
//	private Course course;
	
	@ManyToMany(mappedBy = "teachers")
	private Set<Course> courses;

}
