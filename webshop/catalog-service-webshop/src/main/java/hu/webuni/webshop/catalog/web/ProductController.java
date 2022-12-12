package hu.webuni.webshop.catalog.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import hu.webuni.webshop.catalog.api.ProductControllerApi;
import hu.webuni.webshop.catalog.api.model.ProductDto;

public class ProductController implements ProductControllerApi{

	@Override
	public ResponseEntity<List<ProductDto>> searchProducts(@Valid Integer page, @Valid Integer size, @Valid String sort,
			@Valid Long id, @Valid String name, @Valid String categoryName, @Valid List<Double> price) {
		// TODO Auto-generated method stub
		return ProductControllerApi.super.searchProducts(page, size, sort, id, name, categoryName, price);
	}

}
