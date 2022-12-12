package hu.webuni.webshop.catalog.web;

import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.webshop.catalog.api.CategoryControllerApi;
import hu.webuni.webshop.catalog.api.model.CategoryDto;
import hu.webuni.webshop.catalog.mapper.CategoryMapper;
import hu.webuni.webshop.catalog.model.Category;
import hu.webuni.webshop.catalog.model.Product;
import hu.webuni.webshop.catalog.repository.CategoryRepository;
import hu.webuni.webshop.catalog.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerApi {

	private final CategoryRepository categoryRepository;

	private final CategoryService categoryService;

	private final CategoryMapper categoryMapper;

	@Override
	public ResponseEntity<CategoryDto> createCategory(@Valid CategoryDto categoryDto) {

//		Category category = new Category();
//		category.setName(categoryDto.getName());
//		HashSet<Product> products = new HashSet<>();
//		category.setProducts(products);
//		if ((category = categoryService.createNewCategory(category)) != null) {
//			categoryDto.setId(category.getId());
//			return CategoryControllerApi.super.createCategory(categoryDto);
//		}
//		return ResponseEntity.badRequest().build();

		Category category = categoryMapper.dtoToCategory(categoryDto);
		category = categoryService.createNewCategory(category);
		if (category != null)
			return ResponseEntity.ok(categoryMapper.CategoryToDto(category));
		else
			return ResponseEntity.badRequest().build();
	}
}
