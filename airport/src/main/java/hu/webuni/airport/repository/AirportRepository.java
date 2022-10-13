package hu.webuni.airport.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.airport.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long>{
	
	Long countByIata(String iata);
	
	Long countByIataAndIdNot(String iata, long id);
	
	@EntityGraph(attributePaths = {"address","departures"/*,"arrivals"*/}/*,type = EntityGraphType.LOAD*/)
	@Query("SELECT a FROM Airport a")
	List<Airport> findAllWithAddressAndDepartures(Pageable pageable);
//	@Query("SELECT a FROM Airport a LEFT JOIN FETC a.address")
//	List<Airport> findAllWithAddress();
	
	@EntityGraph(attributePaths = {"arrivals"})
	@Query("SELECT a FROM Airport a")
	List<Airport> findAllWithArrivals(Pageable pageable);
	
	@EntityGraph(attributePaths = {"address"})
	@Query("SELECT a FROM Airport a")
	List<Airport> findAllWithAddress(Pageable pageable);
	
	@EntityGraph(attributePaths = {"arrivals"})
	@Query("SELECT a FROM Airport a WHERE a.id IN :ids")
	List<Airport> findByIdWithArrivals(List<Long> ids);
	
	@EntityGraph(attributePaths = {"departures"})
	@Query("SELECT a FROM Airport a WHERE a.id IN :ids")
//	List<Airport> findByIdWithDepartures(List<Long> ids);
	List<Airport> findByIdWithDepartures(List<Long> ids, Sort sort);
}
