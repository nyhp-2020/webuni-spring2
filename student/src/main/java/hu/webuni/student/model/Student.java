package hu.webuni.student.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.envers.Audited;

import hu.webuni.student.model.UniversityUser.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Data
@Audited
public class Student extends UniversityUser{
	
//	@Id
//	@GeneratedValue
//	@EqualsAndHashCode.Include()
//	@ToString.Include
//	private long id;
	
//	@ToString.Include
//	private String name;
//	private LocalDate birthdate;
	private int semester;

	@ManyToMany(mappedBy = "students")
	private Set<Course> courses;
	
	private long cid; //central id
	private int ufsc; //used_free_semesters_count
	
	private int balance;
	
	@Override
	public UserType getUserType() {
		return UserType.STUDENT;
	}
	
	
}
