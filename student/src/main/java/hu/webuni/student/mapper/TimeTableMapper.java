package hu.webuni.student.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.student.api.model.TimetableDto;
import hu.webuni.student.model.Timetable;




@Mapper(componentModel = "spring")
public interface TimeTableMapper {
	
	@Mapping(target = "courseName", source="course.name")
	@Mapping(target = "startLesson", source="startTime")
	@Mapping(target = "endLesson", source="endTime")
	public TimetableDto timeTableItemToDto(Timetable item);
	
	public List<TimetableDto> timetablesToDtos(List<Timetable> items);

}
