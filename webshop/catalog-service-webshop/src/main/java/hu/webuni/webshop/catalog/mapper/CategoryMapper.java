package hu.webuni.webshop.catalog.mapper;

import java.util.ArrayList;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.webshop.catalog.api.model.CategoryDto;
import hu.webuni.webshop.catalog.api.model.ProductDto;
import hu.webuni.webshop.catalog.model.Category;
import hu.webuni.webshop.catalog.model.Product;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	@Mapping(source = "data",target = "category")
	Product dtoToProduct(ProductDto productDto);
	@Mapping(source = "category",target = "data")
	ProductDto ProductToDto(Product product);
//	@Mapping(target = "products", ignore = true)
	Category dtoToCategory(CategoryDto categoryDto);
//	@Mapping(target = "products", ignore = true)
	CategoryDto CategoryToDto(Category category);
//	Set<Product> dtosToProducts(ArrayList<ProductDto> productDtos);
//	ArrayList<ProductDto> ProductsToDtos(Set<Product> products);
}
