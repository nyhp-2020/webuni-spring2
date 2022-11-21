package hu.webuni.student.xmlws;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import hu.webuni.student.api.model.TimetableDto;
import hu.webuni.student.mapper.TimeTableMapper;
import hu.webuni.student.model.Timetable;
import hu.webuni.student.service.TimeTableService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentXmlWsImpl implements StudentXmlWs{
	
	private final TimeTableService timeTableService;
	private final TimeTableMapper timeTableMapper;

	@Override
	public List<TimetableDto> getApiTimetable(@Valid Long studentId, @Valid Long teacherId,
			@Valid LocalDate from, @Valid LocalDate until){
			ArrayList<TimetableDto> result = new ArrayList<>();

			try {
				if (studentId != null) {

					Map<LocalDate, List<Timetable>> timeTableForStudent = timeTableService.getTimeTableForStudent(studentId,
							from, until);

					for (Map.Entry<LocalDate, List<Timetable>> entry : timeTableForStudent.entrySet()) {
						LocalDate day = entry.getKey();
						List<Timetable> items = entry.getValue();
						List<TimetableDto> itemDtos = timeTableMapper.timetablesToDtos(items);
						itemDtos.forEach(i -> i.setDay(day));
						result.addAll(itemDtos);
					}
					return result;
				}
				// TODO: similar for teacher
				if (teacherId != null) {

					Map<LocalDate, List<Timetable>> timeTableForTeacher = timeTableService.getTimeTableForTeacher(teacherId,
							from, until);

					for (Map.Entry<LocalDate, List<Timetable>> entry : timeTableForTeacher.entrySet()) {
						LocalDate day = entry.getKey();
						List<Timetable> items = entry.getValue();
						List<TimetableDto> itemDtos = timeTableMapper.timetablesToDtos(items);
						itemDtos.forEach(i -> i.setDay(day));
						result.addAll(itemDtos);
					}
					return result;
				}
				return null;
//				return ResponseEntity.badRequest().build();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			}
	}

}
