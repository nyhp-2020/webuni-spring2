package hu.webuni.webshop.catalog.web;

import java.lang.reflect.Method;
import java.util.List;

import javax.validation.Valid;

import org.springframework.core.MethodParameter;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.querydsl.core.types.Predicate;

import hu.webuni.webshop.catalog.api.ProductControllerApi;
import hu.webuni.webshop.catalog.api.model.ProductDto;
import hu.webuni.webshop.catalog.mapper.ProductMapper;
import hu.webuni.webshop.catalog.model.Product;
import hu.webuni.webshop.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerApi{
	
	final QuerydslPredicateArgumentResolver prediacateResolver;
	private final NativeWebRequest nativeWebRequest;
	private final ProductMapper productMapper;
	private final ProductRepository productRepository;

	public void configurePredicate(@QuerydslPredicate(root = Product.class) Predicate predicate) {}
	
	private Predicate createPredicate(String configMethodName) {
		Method method;
		try {
			method = this.getClass().getMethod(configMethodName, Predicate.class);
			MethodParameter methodParameter = new MethodParameter(method, 0);
			ModelAndViewContainer mavContainer = null;
			WebDataBinderFactory binderFactory = null;
			return (Predicate) prediacateResolver.resolveArgument(methodParameter, mavContainer, nativeWebRequest, binderFactory);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public ResponseEntity<List<ProductDto>> searchProducts(@Valid Integer page, @Valid Integer size, @Valid String sort,
			@Valid Long id, @Valid String name, @Valid String categoryName, @Valid List<Double> price) {
		
		Predicate predicate = createPredicate("configurePredicate");
		return ResponseEntity.ok(productMapper.flightsToDtos(productRepository.findAll(predicate)));
	}

}
