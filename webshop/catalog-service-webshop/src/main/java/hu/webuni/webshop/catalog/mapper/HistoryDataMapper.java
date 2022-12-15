package hu.webuni.webshop.catalog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.webshop.catalog.api.model.CategoryDto;
import hu.webuni.webshop.catalog.api.model.HistoryDataProductDto;
import hu.webuni.webshop.catalog.model.Category;
import hu.webuni.webshop.catalog.model.HistoryData;
import hu.webuni.webshop.catalog.model.Product;

@Mapper(componentModel = "spring")
public interface HistoryDataMapper {
	
	HistoryDataProductDto productHistoryDataToDto(HistoryData<Product> hd);
	
	CategoryDto categoryToDto(Category category);

}
