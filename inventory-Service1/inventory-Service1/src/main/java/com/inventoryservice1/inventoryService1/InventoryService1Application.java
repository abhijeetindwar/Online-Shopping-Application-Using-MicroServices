package com.inventoryservice1.inventoryService1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.inventoryservice1.inventoryService1.model.Inventory;
import com.inventoryservice1.inventoryService1.repository.InventoryRepo;

@SpringBootApplication
@EnableEurekaClient
public class InventoryService1Application {

	public static void main(String[] args) {
		SpringApplication.run(InventoryService1Application.class, args);
	}
	@Bean
	public CommandLineRunner loadData(InventoryRepo inventoryRepo) {
		return args->{
			Inventory inventory1=new Inventory();
			inventory1.setSkuCode("Iphon-14-red");
			inventory1.setQuantity(100);
			Inventory inventory2=new Inventory();
			inventory2.setSkuCode("Iphon-14-white");
			inventory2.setQuantity(0);
			inventoryRepo.save(inventory1);
			inventoryRepo.save(inventory2);

			
			
		};
	}

}
