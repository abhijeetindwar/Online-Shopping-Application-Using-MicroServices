package com.inventoryservice1.inventoryService1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventoryservice1.inventoryService1.dto.InventoryResponse;

import com.inventoryservice1.inventoryService1.repository.InventoryRepo;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
	private final InventoryRepo inventoryRepo;
	@Transactional(readOnly = true)
	@SneakyThrows
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		log.info("wait! started");
		Thread.sleep(10000);
		log.info("wait! ended");

		return inventoryRepo.findBySkuCodeIn(skuCode).stream()
				.map(inventory -> 
					InventoryResponse.builder().skuCode(inventory
				.getSkuCode()).isStock(inventory.getQuantity()>0).build()
				).collect(Collectors.toList());
	}

}
