package hu.webuni.airport.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.airport.api.model.AirportDto;
import hu.webuni.airport.api.model.FlightDto;
import hu.webuni.airport.api.model.HistoryDataAirportDto;
import hu.webuni.airport.model.Airport;
import hu.webuni.airport.model.Flight;
import hu.webuni.airport.model.HistoryData;

@Mapper(componentModel = "spring")
public interface HistoryDataMapper {

	HistoryDataAirportDto airportHistoryDataToDto(HistoryData<Airport> hd);
	
	@Mapping(ignore = true, target = "takeoff")
	@Mapping(ignore = true, target = "landing")
	FlightDto flightToDto(Flight flight);

}
