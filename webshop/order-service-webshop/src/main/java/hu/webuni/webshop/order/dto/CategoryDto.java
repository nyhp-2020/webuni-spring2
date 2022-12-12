package hu.webuni.webshop.order.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class CategoryDto {
	private long id;
	private String name;
	ArrayList<ProductDto> products;
}
