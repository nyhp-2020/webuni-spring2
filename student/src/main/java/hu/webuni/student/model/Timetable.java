package hu.webuni.student.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import javax.persistence.ManyToOne;

public class Timetable {
	
	private long id;
	private DayOfWeek dayOfWeek;
	private LocalTime from;
	private LocalTime to;

	@ManyToOne
	Course course;
}
