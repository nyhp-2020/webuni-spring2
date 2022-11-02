package hu.webuni.student.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;

import org.hibernate.envers.Audited;

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
@NamedEntityGraph(
		name = "Course.students",
		attributeNodes = @NamedAttributeNode("students"))
@NamedEntityGraph(
		name = "Course.teachers",
		attributeNodes = @NamedAttributeNode("teachers"))
@Audited
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
	
//	@OneToMany(mappedBy = "course")
	@ManyToMany
	private Set<Teacher> teachers;
	
	@OneToMany(mappedBy = "course")
	Set<Timetable> timetables;

}
