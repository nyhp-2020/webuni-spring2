package hu.webuni.airport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.airport.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long>{
	
	Long countByIata(String iata);
	
	Long countByIataAndIdNot(String iata, long id);
	
	@EntityGraph(attributePaths = {"address","departures"}/*,type = EntityGraphType.LOAD*/)
	@Query("SELECT a FROM Airport a")
	List<Airport> findAllWithAddressAndDepartures();
//	@Query("SELECT a FROM Airport a LEFT JOIN FETC a.address")
//	List<Airport> findAllWithAddress();
}
