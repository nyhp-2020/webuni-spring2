package hu.webuni.webshop.order.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//import hu.webuni.webshop.catalog.model.Product;
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
public class OrderItem {
	
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	long id;
	@EqualsAndHashCode.Include()
	long quantity;
	@EqualsAndHashCode.Include()
	double orderPrice;
	@ManyToOne
	WsOrder wsorder;
//	@ManyToOne
//	Product product;
	@EqualsAndHashCode.Include()
	String productname;
	@EqualsAndHashCode.Include()
	long productId;
}
