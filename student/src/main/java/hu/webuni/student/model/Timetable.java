package hu.webuni.student.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Audited
@Entity
@Cacheable
public class Timetable {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Convert(converter = DayOfWeekToIntegerConverter.class)
	private DayOfWeek dayOfWeek;
	private LocalTime startTime;
	private LocalTime endTime;

	@ManyToOne
	private Course course;
}
