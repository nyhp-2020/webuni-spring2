package hu.webuni.airport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import hu.webuni.airport.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long>,
	JpaSpecificationExecutor<Flight>,
	QuerydslPredicateExecutor<Flight>{
	
}
