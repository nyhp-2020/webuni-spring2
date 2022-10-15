package hu.webuni.airport.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.JoinType;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.airport.model.Airport;
import hu.webuni.airport.model.HistoryData;
import hu.webuni.airport.repository.AirportRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AirportService {

	private final AirportRepository airportRepository;
	
	@PersistenceContext  //injektálás
	private EntityManager em;

	@Transactional
	public Airport save(Airport airport) {
		checkUniqueIata(airport.getIata(), null);
		return airportRepository.save(airport);
	}
	
	@Transactional
	public Airport update(Airport airport) {
		checkUniqueIata(airport.getIata(), airport.getId());
		if(airportRepository.existsById(airport.getId())) {
			return airportRepository.save(airport);
		}
		else
			throw new NoSuchElementException();
	}
	
	private void checkUniqueIata(String iata, Long id) {
		
		boolean forUpdate = id != null;
		Long count = forUpdate ?
				airportRepository.countByIataAndIdNot(iata, id)
				:airportRepository.countByIata(iata);
		
		if(count > 0)
			throw new NonUniqueIataException(iata);
	}
	
	public List<Airport> findAll(){
		return airportRepository.findAll();
	}
	
	public Optional<Airport> findById(long id){
		return airportRepository.findById(id);
	}
	
	@Transactional
	public void delete(long id) {
		airportRepository.deleteById(id);
	}

	@Transactional
	@Cacheable("pagedAirportsWithRelationships")
	public List<Airport> findAllWithRelationships(Pageable pageable) {
//		List<Airport> airports = airportRepository.findAllWithAddressAndDepartures(pageable); --> in memory lapozás, minden sor bejön a DB-ből
//		airports = airportRepository.findAllWithArrivals(pageable);
		
		List<Airport> airports = airportRepository.findAllWithAddress(pageable);
		List<Long> arportIds = airports.stream().map(Airport::getId).toList();
		
		airports = airportRepository.findByIdWithArrivals(arportIds);
//		airports = airportRepository.findByIdWithDepartures(arportIds);
		
		airports = airportRepository.findByIdWithDepartures(arportIds, pageable.getSort());
		
		return airports;
	}

//	@Transactional
//	@SuppressWarnings({"rawtypes","unchecked"})
//	public List<Airport> getAirportHistory(long id){
//		List resultList = AuditReaderFactory.get(em)
//		.createQuery()
//		.forRevisionsOfEntity(Airport.class, true, true) //revision infók nem
//		.add(AuditEntity.property("id").eq(id))
//		.getResultList();
//		
//		return resultList;
//	}
	
//	@Transactional
//	@SuppressWarnings({"rawtypes","unchecked"})
//	public List<HistoryData<Airport>> getAirportHistory(long id){
//		
//		List resultList = AuditReaderFactory.get(em)
//		.createQuery()
//		.forRevisionsOfEntity(Airport.class, false, true) //revision infók is
//		.add(AuditEntity.property("id").eq(id))
//		.getResultList()
//		.stream().map(o ->{
//			Object[] objArray = (Object[])o;
//			DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity)objArray[1];
//			return new HistoryData<Airport>(
//					(Airport)objArray[0],
//					(RevisionType)objArray[2],
//					revisionEntity.getId(),
//					revisionEntity.getRevisionDate()
//					);
//		}).toList();
//		
//		return resultList;
//	}
	
//	@Transactional
//	@SuppressWarnings({"rawtypes","unchecked"})
//	public List<HistoryData<Airport>> getAirportHistory(long id){
//		
//		List resultList = AuditReaderFactory.get(em)
//		.createQuery()
//		.forRevisionsOfEntity(Airport.class, false, true) //revision infók is
//		.add(AuditEntity.property("id").eq(id))
//		.traverseRelation("address", JoinType.LEFT)
//		.getResultList()
//		.stream().map(o ->{
//			Object[] objArray = (Object[])o;
//			DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity)objArray[1];
//			return new HistoryData<Airport>(
//					(Airport)objArray[0],
//					(RevisionType)objArray[2],
//					revisionEntity.getId(),
//					revisionEntity.getRevisionDate()
//					);
//		}).toList();
//		
//		return resultList;
//	}
	
	@Transactional
	@SuppressWarnings({"rawtypes","unchecked"})
	public List<HistoryData<Airport>> getAirportHistory(long id){
		
		List resultList = AuditReaderFactory.get(em)
		.createQuery()
		.forRevisionsOfEntity(Airport.class, false, true) //revision infók is
		.add(AuditEntity.property("id").eq(id))
//		.traverseRelation("address", JoinType.LEFT)
		.getResultList()
		.stream().map(o ->{
			Object[] objArray = (Object[])o;
			DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity)objArray[1];
			Airport airport = (Airport)objArray[0];
			airport.getAddress().getCity(); //kikényszeríti a betöltést
			airport.getArrivals().size();
			airport.getDepartures().size();
			return new HistoryData<Airport>(
					airport,
					(RevisionType)objArray[2],
					revisionEntity.getId(),
					revisionEntity.getRevisionDate()
					);
		}).toList();
		
		return resultList;
	}
	
	
}
