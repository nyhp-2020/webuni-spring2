package hu.webuni.student.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.student.api.TimeTableControllerApi;
import hu.webuni.student.api.model.TimetableDto;
import hu.webuni.student.mapper.TimeTableMapper;
import hu.webuni.student.model.Timetable;
import hu.webuni.student.service.TimeTableService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TimeTableController implements TimeTableControllerApi {

	private final TimeTableService timeTableService;
	private final TimeTableMapper timeTableMapper;

	@Override
	public ResponseEntity<List<TimetableDto>> getApiTimetable(@Valid Long studentId, @Valid Long teacherId,
			@Valid LocalDate from, @Valid LocalDate until) {

		ArrayList<TimetableDto> result = new ArrayList<>();

		try {
			if (studentId != null) {
//				ArrayList<TimetableDto> result = new ArrayList<>();

				Map<LocalDate, List<Timetable>> timeTableForStudent = timeTableService.getTimeTableForStudent(studentId,
						from, until);

				for (Map.Entry<LocalDate, List<Timetable>> entry : timeTableForStudent.entrySet()) {
					LocalDate day = entry.getKey();
					List<Timetable> items = entry.getValue();
					List<TimetableDto> itemDtos = timeTableMapper.timetablesToDtos(items);
					itemDtos.forEach(i -> i.setDay(day));
					result.addAll(itemDtos);
				}
				return ResponseEntity.ok(result);
			}
			// TODO: similar for teacher
			if (teacherId != null) {
//				ArrayList<TimetableDto> result = new ArrayList<>();

				Map<LocalDate, List<Timetable>> timeTableForTeacher = timeTableService.getTimeTableForTeacher(teacherId,
						from, until);

				for (Map.Entry<LocalDate, List<Timetable>> entry : timeTableForTeacher.entrySet()) {
					LocalDate day = entry.getKey();
					List<Timetable> items = entry.getValue();
					List<TimetableDto> itemDtos = timeTableMapper.timetablesToDtos(items);
					itemDtos.forEach(i -> i.setDay(day));
					result.addAll(itemDtos);
				}
				return ResponseEntity.ok(result);
			}

//			else
			return ResponseEntity.badRequest().build();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@Override
	public ResponseEntity<TimetableDto> getApiTimetableSearch(@Valid Long studentId, @Valid Long teacherId,
			@Valid LocalDate from, @Valid String course) {
		Entry<LocalDate, Timetable> foundTimeTableEntry = null;
		if (studentId != null) {
			foundTimeTableEntry = timeTableService.searchTimeTableOfStudent(studentId, from,
					course);
			if (foundTimeTableEntry == null)
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if (teacherId != null && foundTimeTableEntry == null) {
			foundTimeTableEntry = timeTableService.searchTimeTableOfTeacher(teacherId, from,
					course);
			if (foundTimeTableEntry == null)
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		if(foundTimeTableEntry == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		TimetableDto timeTableItemDto = timeTableMapper.timeTableItemToDto(foundTimeTableEntry.getValue());
		timeTableItemDto.setDay(foundTimeTableEntry.getKey());

		return ResponseEntity.ok(timeTableItemDto);
	}

}
