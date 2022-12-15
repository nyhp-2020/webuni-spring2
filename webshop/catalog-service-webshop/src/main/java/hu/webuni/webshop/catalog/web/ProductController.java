package hu.webuni.webshop.catalog.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.core.MethodParameter;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import com.querydsl.core.types.Predicate;

import hu.webuni.webshop.catalog.api.ProductControllerApi;
import hu.webuni.webshop.catalog.api.model.HistoryDataProductDto;
import hu.webuni.webshop.catalog.api.model.ProductDto;
import hu.webuni.webshop.catalog.mapper.HistoryDataMapper;
import hu.webuni.webshop.catalog.mapper.ProductMapper;
import hu.webuni.webshop.catalog.model.Category;
import hu.webuni.webshop.catalog.model.HistoryData;
import hu.webuni.webshop.catalog.model.Product;
import hu.webuni.webshop.catalog.repository.CategoryRepository;
import hu.webuni.webshop.catalog.repository.ProductRepository;
import hu.webuni.webshop.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerApi {

	final QuerydslPredicateArgumentResolver prediacateResolver;
	private final NativeWebRequest nativeWebRequest;
	private final ProductMapper productMapper;
	private final ProductRepository productRepository;
	private final ProductService productService;
	private final CategoryRepository categoryRepository;
	private final HistoryDataMapper historyDataMapper;

	public void configurePredicate(@QuerydslPredicate(root = Product.class) Predicate predicate) {
	}

	private Predicate createPredicate(String configMethodName) {
		Method method;
		try {
			method = this.getClass().getMethod(configMethodName, Predicate.class);
			MethodParameter methodParameter = new MethodParameter(method, 0);
			ModelAndViewContainer mavContainer = null;
			WebDataBinderFactory binderFactory = null;
			return (Predicate) prediacateResolver.resolveArgument(methodParameter, mavContainer, nativeWebRequest,
					binderFactory);
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

	@Override
	public ResponseEntity<ProductDto> createProduct(@Valid ProductDto productDto) {
		Product product = productMapper.dtoToProduct(productDto);
		if (productDto.getCategory() == null) {
			product =productRepository.save(product);
		} else {
			if(product.getCategory().getId() != 0)
				product = productService.createNewProduct(product);
			else {
				product.setCategory(null);
				product =productRepository.save(product);
			}
		}
		return ResponseEntity.ok(productMapper.ProductToDto(product));
	}

	@Override
	public ResponseEntity<Void> deleteProduct(Long id) {
		productRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<ProductDto> modifyProduct(Long id, @Valid ProductDto productDto) {

		Product product = productMapper.dtoToProduct(productDto);
		product.setId(id);
		
		Category category = product.getCategory();
		if(category != null)
			categoryRepository.findById(category.getId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		try {
			ProductDto savedProductDto = productMapper.ProductToDto(productService.update(product));

			return ResponseEntity.ok(savedProductDto);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<List<HistoryDataProductDto>> getApiProductsIdHistory(Long id) {
		
		List<HistoryData<Product>> products = productService.getProductHistory(id);
		
		List<HistoryDataProductDto> productDtosWithHistory = new ArrayList<>();
		
		products.forEach(hd ->{
			productDtosWithHistory.add(historyDataMapper.productHistoryDataToDto(hd));
		});
		
		return ResponseEntity.ok(productDtosWithHistory);
	}
	
	
}
