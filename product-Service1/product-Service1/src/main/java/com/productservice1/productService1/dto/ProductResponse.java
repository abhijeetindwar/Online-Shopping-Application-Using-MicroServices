package com.productservice1.productService1.dto;





import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
	private long id;
	private String name;
	private String describtion;
	private long price;

}
