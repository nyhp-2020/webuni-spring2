package hu.webuni.webshop.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import hu.webuni.tokenlib.JwtAuthFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableCaching
@SpringBootApplication(scanBasePackageClasses = {JwtAuthFilter.class, CatalogServiceWebshopApplication.class})
public class CatalogServiceWebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceWebshopApplication.class, args);
	}

}
