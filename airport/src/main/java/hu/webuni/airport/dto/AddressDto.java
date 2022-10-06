package hu.webuni.airport.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private long id;
	
	private String country;
	private String city;
	private String street;
	private String zip;
	
}
