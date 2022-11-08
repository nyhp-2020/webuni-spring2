package hu.webuni.student.model;

import java.time.DayOfWeek;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DayOfWeekToIntegerConverter implements AttributeConverter<DayOfWeek, Integer>{

	@Override
	public Integer convertToDatabaseColumn(DayOfWeek attribute) {
		return attribute == null ? null : attribute.getValue();
	}

	@Override
	public DayOfWeek convertToEntityAttribute(Integer dbData) {
		return dbData == null ? null : DayOfWeek.of(dbData);
	}

}
