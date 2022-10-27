package hu.webuni.student.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import hu.webuni.student.api.model.GetAvgOfSemesterOfStudents200ResponseInner;

@Mapper(componentModel = "spring")
public interface ObjectArrayDataMapper {
	@IterableMapping(qualifiedByName = "objtoinner")
	List<GetAvgOfSemesterOfStudents200ResponseInner> objsToInners(List<Object> lo);
	@Named("objtoinner")
	GetAvgOfSemesterOfStudents200ResponseInner objToInner(Object o);
}
