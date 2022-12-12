package hu.webuni.webshop.catalog.repository;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import hu.webuni.webshop.catalog.model.Product;
import hu.webuni.webshop.catalog.model.QProduct;

public interface ProductRepository extends JpaRepository<Product, Long>,
										QuerydslPredicateExecutor<Product>,
										QuerydslBinderCustomizer<QProduct>,
										QueryDslWithEntityGraphRepository<Product,Long>{

	@Override
	default void customize(QuerydslBindings bindings, QProduct product) {
//		bindings.bind(product.id).first((path, value) -> path.eq(value));
		bindings.bind(product.name).first((path, value) -> path.startsWithIgnoreCase(value));
		bindings.bind(product.category.name).first((path, value) -> path.startsWithIgnoreCase(value));
		bindings.bind(product.price).all((path, values) -> {
			if(values.size() != 2)
				return Optional.empty();
			
			Iterator<? extends Double> iterator = values.iterator();
			Double from = iterator.next();
			Double to = iterator.next();
			
			return Optional.of(path.between(from, to));
		});
		
	}

}
