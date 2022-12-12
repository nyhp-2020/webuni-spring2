package hu.webuni.webshop.order.dto;


import lombok.Data;
@Data
public class ProductDto {
	private long id;
	String name;
	double price;
	CategoryDto category;
}
