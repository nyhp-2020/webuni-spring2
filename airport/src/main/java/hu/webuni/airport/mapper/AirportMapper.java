package hu.webuni.airport.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.airport.dto.AirportDto;
import hu.webuni.airport.model.Airport;

@Mapper(componentModel = "spring")
public interface AirportMapper {

	List<AirportDto> airportsToDtos(List<Airport> airports);
	
	@IterableMapping(qualifiedByName = "summary")
	List<AirportDto> airportSummariesToDtos(List<Airport> airports);

	AirportDto airportToDto(Airport airport);
	
	@Named("summary")
	@Mapping(target = "address", ignore = true)
	AirportDto airportSummaryToDto(Airport airport);

	Airport dtoToAirport(AirportDto airportDto);
}
