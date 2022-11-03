package hu.webuni.airport.xmlws;

import java.util.List;

import javax.jws.WebService;

import hu.webuni.airport.api.model.HistoryDataAirportDto;

@WebService
public interface AirportXmlWs {
	
	public List<HistoryDataAirportDto> getHistoryById(Long id);

}
