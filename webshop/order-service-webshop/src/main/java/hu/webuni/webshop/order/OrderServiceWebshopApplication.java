package hu.webuni.webshop.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
//@SpringBootApplication(scanBasePackageClasses = {CatalogServiceWebshopApplication.class ,OrderServiceWebshopApplication.class})
public class OrderServiceWebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceWebshopApplication.class, args);
	}

}
