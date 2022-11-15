package hu.webuni.student.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import hu.webuni.student.model.Semester;
import hu.webuni.student.model.SpecialDay;
import hu.webuni.student.model.Timetable;
import hu.webuni.student.repository.SpecialDayRepository;
import hu.webuni.student.repository.StudentRepository;
import hu.webuni.student.repository.TeacherRepository;
import hu.webuni.student.repository.TimetableRepository;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class TimeTableService {
	
	private final SpecialDayRepository specialDayRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final TimetableRepository timetableRepository;
	
	
	@Autowired
	@Lazy
	private TimeTableService self;
	
	@Cacheable("studentTimetableResults")
	public Map<LocalDate, List<Timetable>> getTimeTableForStudent(long studentId, LocalDate from, LocalDate until) {
		
		Map<LocalDate, List<Timetable>> timeTable = new LinkedHashMap<>(); //tartja a sorrendet
		
		Semester semester = Semester.fromMidSemesterDay(from);
		Semester semesterOfUntil = Semester.fromMidSemesterDay(until);
		if(!semester.equals(semesterOfUntil)) {
			throw new IllegalArgumentException("from and until should be in the same semester");
		}
		
		List<Timetable> relevantTimeTableItems = findBySudentAndSemester(studentId, semester);
		
		Map<DayOfWeek, List<Timetable>> timeTableItemsByDayOfWeek = 
				relevantTimeTableItems.stream().collect(Collectors.groupingBy(Timetable::getDayOfWeek));
		
		List<SpecialDay> specialDaysAffected = specialDayRepository.findBySourceDayOrTargetDay(from, until);
		Map<LocalDate, List<SpecialDay>> specialDaysBySourceDay = specialDaysAffected.stream()
				.collect(Collectors.groupingBy(SpecialDay::getSourceDay));
		
		Map<LocalDate, List<SpecialDay>> specialDaysByTargetDay = specialDaysAffected.stream()
				.filter(sd -> sd.getTargetDay() != null)
				.collect(Collectors.groupingBy(SpecialDay::getTargetDay));
		
		for(LocalDate day = from; !day.isAfter(until); day = day.plusDays(1)) {
			
			List<Timetable> itemsOnDay = new ArrayList<>();
//			int dayOfWeek = day.getDayOfWeek().getValue();
			DayOfWeek dayOfWeek = day.getDayOfWeek();
			List<Timetable> normalItemsOnDay = timeTableItemsByDayOfWeek.get(dayOfWeek);
			
			if(normalItemsOnDay != null && isDayNotFreeNeitherSwapped(specialDaysBySourceDay, day))
				itemsOnDay.addAll(normalItemsOnDay);
			
//			Integer dayOfWeekMovedToThisDay = getDayOfWeekMovedToThisDay(specialDaysByTargetDay, day);
			
			DayOfWeek dayOfWeekMovedToThisDay = getDayOfWeekMovedToThisDay(specialDaysByTargetDay, day);
//			System.out.println("dayOfWeekMovedToThisDay:" + dayOfWeekMovedToThisDay);
			if(dayOfWeekMovedToThisDay != null) {
				List<Timetable> c = timeTableItemsByDayOfWeek.get(dayOfWeekMovedToThisDay);
				if(c != null)
					itemsOnDay.addAll(c);
			}
			
			itemsOnDay.sort(Comparator.comparing(Timetable::getStartTime));
			
			timeTable.put(day, itemsOnDay);
		}
		
		
		return timeTable;
	}
	
	@Cacheable("studentTimetableResults")
	public Map<LocalDate, List<Timetable>> getTimeTableForTeacher(long teacherId, LocalDate from, LocalDate until) {
		
		Map<LocalDate, List<Timetable>> timeTable = new LinkedHashMap<>(); //tartja a sorrendet
		
		Semester semester = Semester.fromMidSemesterDay(from);
		Semester semesterOfUntil = Semester.fromMidSemesterDay(until);
		if(!semester.equals(semesterOfUntil)) {
			throw new IllegalArgumentException("from and until should be in the same semester");
		}
		
		List<Timetable> relevantTimeTableItems = findByTeacherAndSemester(teacherId, semester);
		
		Map<DayOfWeek, List<Timetable>> timeTableItemsByDayOfWeek = 
				relevantTimeTableItems.stream().collect(Collectors.groupingBy(Timetable::getDayOfWeek));
		
		List<SpecialDay> specialDaysAffected = specialDayRepository.findBySourceDayOrTargetDay(from, until);
		Map<LocalDate, List<SpecialDay>> specialDaysBySourceDay = specialDaysAffected.stream()
				.collect(Collectors.groupingBy(SpecialDay::getSourceDay));
		
		Map<LocalDate, List<SpecialDay>> specialDaysByTargetDay = specialDaysAffected.stream()
				.filter(sd -> sd.getTargetDay() != null)
				.collect(Collectors.groupingBy(SpecialDay::getTargetDay));
		
		for(LocalDate day = from; !day.isAfter(until); day = day.plusDays(1)) {
			
			List<Timetable> itemsOnDay = new ArrayList<>();

			DayOfWeek dayOfWeek = day.getDayOfWeek();
			List<Timetable> normalItemsOnDay = timeTableItemsByDayOfWeek.get(dayOfWeek);
			
			if(normalItemsOnDay != null && isDayNotFreeNeitherSwapped(specialDaysBySourceDay, day))
				itemsOnDay.addAll(normalItemsOnDay);
			
			
			DayOfWeek dayOfWeekMovedToThisDay = getDayOfWeekMovedToThisDay(specialDaysByTargetDay, day);
			if(dayOfWeekMovedToThisDay != null) {
				List<Timetable> c = timeTableItemsByDayOfWeek.get(dayOfWeekMovedToThisDay);
				if(c != null)
					itemsOnDay.addAll(c);
			}
			
			itemsOnDay.sort(Comparator.comparing(Timetable::getStartTime));
			
			timeTable.put(day, itemsOnDay);
		}
		
		
		return timeTable;
	}


	private List<Timetable> findBySudentAndSemester(long studentId, Semester semester) {
		if(!studentRepository.existsById(studentId)) {
			throw new IllegalArgumentException("student does not exist");
		}
		
		List<Timetable> relevantTimeTableItems = timetableRepository.findByStudentAndSemester(studentId, semester.getYear(), semester.getSemesterType());
		return relevantTimeTableItems;
	}
	
	private List<Timetable> findByTeacherAndSemester(long teacherId, Semester semester) {
		if(!teacherRepository.existsById(teacherId)) {
			throw new IllegalArgumentException("student does not exist");
		}
		
		List<Timetable> relevantTimeTableItems = timetableRepository.findByTeacherAndSemester(teacherId, semester.getYear(), semester.getSemesterType());
		return relevantTimeTableItems;
	}
	
	public Map.Entry<LocalDate, Timetable> searchTimeTableOfStudent(long studentId, LocalDate from, String courseName) {
		Map.Entry<LocalDate, Timetable> result = null;
		
		//call in self makes sure that the AOP proxy considers the @Cacheable annotation
		Map<LocalDate, List<Timetable>> timeTableForStudent = self.getTimeTableForStudent(studentId, from, Semester.fromMidSemesterDay(from).getSemesterEnd());
		
		for (Map.Entry<LocalDate, List<Timetable>> entry : timeTableForStudent.entrySet()) {
			LocalDate day = entry.getKey();
			List<Timetable> itemsOnDay = entry.getValue();
			for (Timetable tti : itemsOnDay) {
				if(tti.getCourse().getName().toLowerCase().startsWith(courseName.toLowerCase())) {
					result = Map.entry(day, tti);
					break;
				}
			}
			if(result != null)
				break;
		}
		return result;
			
	}
	
	public Map.Entry<LocalDate, Timetable> searchTimeTableOfTeacher(long teacherId, LocalDate from, String courseName) {
		Map.Entry<LocalDate, Timetable> result = null;
		
		//call in self makes sure that the AOP proxy considers the @Cacheable annotation
		Map<LocalDate, List<Timetable>> timeTableForTeacher = self.getTimeTableForTeacher(teacherId, from, Semester.fromMidSemesterDay(from).getSemesterEnd());
		
		for (Map.Entry<LocalDate, List<Timetable>> entry : timeTableForTeacher.entrySet()) {
			LocalDate day = entry.getKey();
			List<Timetable> itemsOnDay = entry.getValue();
			for (Timetable tti : itemsOnDay) {
				if(tti.getCourse().getName().toLowerCase().startsWith(courseName.toLowerCase())) {
					result = Map.entry(day, tti);
					break;
				}
			}
			if(result != null)
				break;
		}
		return result;
			
	}

//	private Integer getDayOfWeekMovedToThisDay(Map<LocalDate, List<SpecialDay>> specialDaysByTargetDay, LocalDate day) {
	private DayOfWeek getDayOfWeekMovedToThisDay(Map<LocalDate, List<SpecialDay>> specialDaysByTargetDay, LocalDate day) {		
		List<SpecialDay> movedToThisDay = specialDaysByTargetDay.get(day);
		if(movedToThisDay == null || movedToThisDay.isEmpty())
			return null;
		
//		return movedToThisDay.get(0).getSourceDay().getDayOfWeek().getValue();
		return movedToThisDay.get(0).getSourceDay().getDayOfWeek();
	}

	private boolean isDayNotFreeNeitherSwapped(Map<LocalDate, List<SpecialDay>> specialDaysBySourceDay, LocalDate day) {
		List<SpecialDay> specialDaysOnDay = specialDaysBySourceDay.get(day);
		return specialDaysOnDay == null || specialDaysOnDay.isEmpty();
	}


}
