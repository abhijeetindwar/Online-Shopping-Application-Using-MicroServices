package com.productservice1.productService1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductService1Application {

	public static void main(String[] args) {
		SpringApplication.run(ProductService1Application.class, args);
	}

}
