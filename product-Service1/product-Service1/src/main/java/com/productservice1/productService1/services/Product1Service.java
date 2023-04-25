package com.productservice1.productService1.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.productservice1.productService1.dto.ProductRequest;
import com.productservice1.productService1.dto.ProductResponse;
import com.productservice1.productService1.model.Product;
import com.productservice1.productService1.repository.Product1Repo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class Product1Service {
	private final Product1Repo productRepo;
	
	
	
	  
	public void createProduct(ProductRequest productReq) {
		Product product=Product.builder().name(productReq.getName()).describtion(productReq.getDescribtion()).price(productReq.getPrice()).build();
	     productRepo.save(product);
	     log.info("Product {} is Saved",product.getId());
	
	}

	public List<ProductResponse> getAllProducts() {
		// TODO Auto-generated method stub
		List<Product> list=productRepo.findAll();
	
		return 	list.stream().map(product->mapToResponse(product)).collect(Collectors.toList());
		
	}

	private ProductResponse mapToResponse(Product product) {
		// TODO Auto-generated method stub
		return ProductResponse.builder().id(product.getId()).name(product.getName()).describtion(product.getDescribtion()).price(product.getPrice()).build();
		
		
	}

}
