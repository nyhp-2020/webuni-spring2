package hu.webuni.student.xmlws;

import java.time.LocalDate;
import java.util.List;

import javax.jws.WebService;
import javax.validation.Valid;

import hu.webuni.student.api.model.TimetableDto;

@WebService
public interface StudentXmlWs {
	
//	List<TimetableDto> getApiTimetable(@Valid Long studentId, @Valid Long teacherId,
//			@XmlJavaTypeAdapter(value = LocalDateXmlAdapter.class) from, @Valid LocalDate until);
	
	List<TimetableDto> getApiTimetable(@Valid Long studentId, @Valid Long teacherId,
			@Valid LocalDate from, @Valid LocalDate until);

}
