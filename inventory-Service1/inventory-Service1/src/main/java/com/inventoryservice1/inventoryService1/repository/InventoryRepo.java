package com.inventoryservice1.inventoryService1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventoryservice1.inventoryService1.model.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

	

	

	List<Inventory> findBySkuCodeIn(List<String> skuCode);

}