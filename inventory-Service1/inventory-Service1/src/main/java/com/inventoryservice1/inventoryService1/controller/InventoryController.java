package com.inventoryservice1.inventoryService1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inventoryservice1.inventoryService1.dto.InventoryResponse;
import com.inventoryservice1.inventoryService1.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	private final InventoryService inventoryService;
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> hasStock(@RequestParam List<String> skuCode) {
		return inventoryService.isInStock(skuCode);
		
	}

}
