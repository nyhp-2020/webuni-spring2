package hu.webuni.student.mapper;

import org.mapstruct.Mapper;

import hu.webuni.student.api.model.HistoryDataCourseDto;
import hu.webuni.student.model.Course;
import hu.webuni.student.model.HistoryData;

@Mapper(componentModel = "spring")
public interface HistoryDataMapper {

	HistoryDataCourseDto courseHistoryDataToDto(HistoryData<Course> hd);
	

}
