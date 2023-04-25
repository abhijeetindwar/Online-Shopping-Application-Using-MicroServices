package com.productservice1.productService1.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
	private String name;
	private String describtion;
	private long price;
	

}
