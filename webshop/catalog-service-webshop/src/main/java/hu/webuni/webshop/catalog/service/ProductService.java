package hu.webuni.webshop.catalog.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import hu.webuni.webshop.catalog.model.Product;
import hu.webuni.webshop.catalog.model.QProduct;
import hu.webuni.webshop.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	
	@Transactional
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	@Transactional
	public Product update(Product product) {
		if(productRepository.existsById(product.getId()))
				return productRepository.save(product);
		else
			throw new NoSuchElementException();
	}
	
	@Transactional
	public void delete(long id) {
		productRepository.deleteById(id);
	}

	@Transactional
	public List<Product> searchProducts(Predicate predicate,Pageable pageable){
		
		Page<Product> productPage = productRepository.findAll(predicate, pageable);
		BooleanExpression inPredicate = QProduct.product.in(productPage.getContent());
		List<Product> products = productRepository.findAll(inPredicate,"Product.category",EntityGraphType.LOAD, pageable.getSort());
		return products;
	}


}
