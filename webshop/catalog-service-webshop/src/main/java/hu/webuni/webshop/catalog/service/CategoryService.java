package hu.webuni.webshop.catalog.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.webshop.catalog.model.Category;
import hu.webuni.webshop.catalog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	@Transactional
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	@Transactional
	public Category update(Category category) {
		if(categoryRepository.existsById(category.getId()))
				return categoryRepository.save(category);
		else
			throw new NoSuchElementException();
	}
	
	@Transactional
	public void delete(long id) {
		categoryRepository.deleteById(id);
	}

	@Transactional
	public Category createNewCategory(Category category) {
		if(!categoryRepository.existsByName(category.getName()))
			return categoryRepository.save(category);
		return null;
	}

}
