package hu.webuni.airport.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import hu.webuni.airport.api.FlightControllerApi;
import hu.webuni.airport.api.model.FlightDto;
import hu.webuni.airport.mapper.FlightMapper;
import hu.webuni.airport.model.Flight;
import hu.webuni.airport.repository.FlightRepository;
import hu.webuni.airport.service.FlightService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FlightController implements FlightControllerApi{
	
	private final NativeWebRequest nativeWebRequest;
	private final FlightService flightService;
	private final FlightRepository flightRepository;
	private final FlightMapper flightMapper;
	
	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.of(nativeWebRequest);
	}

	@Override
	public ResponseEntity<FlightDto> createFlight(@Valid FlightDto flightDto) {
		Flight flight = flightService.save(flightMapper.dtoToFlight(flightDto));
		return ResponseEntity.ok( flightMapper.flightToDto(flight));
	}

	@Override
	public ResponseEntity<List<FlightDto>> searchFlights(@Valid FlightDto example) {
		return ResponseEntity.ok(flightMapper.flightsToDtos(flightService.findFlightsByExample(flightMapper.dtoToFlight(example))));
	}

	@Override
	public ResponseEntity<Void> startDelayPolling(Long flightId, Long rate) {
		flightService.startDelayPollingForFlight(flightId, rate);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> stopDelayPolling(Long flightId) {
		flightService.stopDelayPollingForFlight(flightId);
		return ResponseEntity.ok().build();
	}

}
