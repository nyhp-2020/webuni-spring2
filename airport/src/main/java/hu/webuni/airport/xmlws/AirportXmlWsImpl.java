package hu.webuni.airport.xmlws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import hu.webuni.airport.api.model.HistoryDataAirportDto;
import hu.webuni.airport.mapper.HistoryDataMapper;
import hu.webuni.airport.model.Airport;
import hu.webuni.airport.model.HistoryData;
import hu.webuni.airport.service.AirportService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirportXmlWsImpl implements AirportXmlWs {

	private final AirportService airportService;

	private final HistoryDataMapper historyDataMapper;

	@Override
	public List<HistoryDataAirportDto> getHistoryById(Long id) {
		List<HistoryData<Airport>> airports = airportService.getAirportHistory(id);

		List<HistoryDataAirportDto> airportDtosWithHistory = new ArrayList<>();

		airports.forEach(hd -> {
			airportDtosWithHistory.add(historyDataMapper.airportHistoryDataToDto(hd));
		});

		return airportDtosWithHistory;
	}

}
