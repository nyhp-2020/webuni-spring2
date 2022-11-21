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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Audited
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
public class Teacher extends UniversityUser{
	
//	@Id
//	@GeneratedValue
//	@EqualsAndHashCode.Include()
//	@ToString.Include
//	private long id;
//	
//	@ToString.Include
//	private String name;
//	private LocalDate birthdate;
	
	
	@ManyToMany(mappedBy = "teachers")
	private Set<Course> courses;

	@Override
	public UserType getUserType() {
		return UserType.TEACHER;
	}
}
