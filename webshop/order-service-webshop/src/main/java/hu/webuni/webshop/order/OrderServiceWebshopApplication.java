package hu.webuni.webshop.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.tokenlib.JwtAuthFilter;






//@SpringBootApplication
//@SpringBootApplication(scanBasePackageClasses = {ProductDto.class ,OrderServiceWebshopApplication.class})
@SpringBootApplication(scanBasePackageClasses = {JwtAuthFilter.class, OrderServiceWebshopApplication.class})
public class OrderServiceWebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceWebshopApplication.class, args);
	}

}
