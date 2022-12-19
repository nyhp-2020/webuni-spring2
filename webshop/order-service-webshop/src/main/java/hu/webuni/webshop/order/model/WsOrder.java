package hu.webuni.webshop.order.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class WsOrder {
	
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
	
	@OneToMany(mappedBy = "wsorder")
	Set<OrderItem> items;
	
//	@ManyToOne
//	WebshopUser webshopUser;
	String username;
	OrderState orderState;
	
	public void addOrderItem(OrderItem orderItem) {
		orderItem.setWsorder(this);
		if(this.items == null)
			this.items = new HashSet<>();
		this.items.add(orderItem);
	}
}
