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
public class Product {
	
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private long id;
	String name;
	double price;
	@ManyToOne
	Category category;
//	@OneToMany(mappedBy = "product")
//	Set<OrderItem> items;
}
