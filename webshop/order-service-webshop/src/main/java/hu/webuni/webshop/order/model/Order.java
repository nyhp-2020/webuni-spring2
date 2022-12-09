package hu.webuni.webshop.order.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Order {
	
	public enum OrderState {
		PENDING, 
		CONFIRMED,
		DECLINED,
		SHIPMENT_FAILED,
		DELIVERED
		;
	}
	
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	long id;
	String deliveryAddress;
	
	@OneToMany(mappedBy = "order")
	Set<Item> items;
	
//	@ManyToOne
//	WebshopUser webshopUser;
	
	OrderState orderState;
}
