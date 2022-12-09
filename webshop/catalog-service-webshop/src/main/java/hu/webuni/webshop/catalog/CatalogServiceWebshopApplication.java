package hu.webuni.webshop.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
@EnableCaching
public class CatalogServiceWebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceWebshopApplication.class, args);
	}

}
