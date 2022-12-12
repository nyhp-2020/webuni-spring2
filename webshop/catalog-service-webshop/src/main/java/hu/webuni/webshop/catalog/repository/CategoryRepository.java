package hu.webuni.webshop.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.webshop.catalog.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	boolean existsByName(String name);
}
