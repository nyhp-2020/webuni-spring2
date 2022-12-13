package hu.webuni.webshop.catalog.model;

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
public class Category {
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private long id;
	private String name;
	@OneToMany(mappedBy = "category")
	Set<Product> products;
	
	public void addProduct(Product product) {
		product.setCategory(this);
		if(this.products == null)
			this.products = new HashSet<>();
		this.products.add(product);
	}

}
