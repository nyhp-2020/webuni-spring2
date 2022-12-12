package hu.webuni.webshop.catalog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.webshop.catalog.api.model.CategoryDto;
import hu.webuni.webshop.catalog.api.model.ProductDto;
import hu.webuni.webshop.catalog.model.Category;
import hu.webuni.webshop.catalog.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	
//	Category dtoToCategory(CategoryDto categoryDto);
//	CategoryDto CategoryToDto(Category category);
	@Mapping(source = "data",target = "category")
	Product dtoToProduct(ProductDto productDto);
	@Mapping(source = "category",target = "data")
	ProductDto ProductToDto(Product product);	

}
