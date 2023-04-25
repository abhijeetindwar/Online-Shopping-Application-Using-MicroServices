package com.orderservice1.orderService1.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice1.orderService1.dto.OrderRequest;
import com.orderservice1.orderService1.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
     private final OrderService orderService; 
     
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name="inventory",fallbackMethod = "fallBackMethod")
	@TimeLimiter(name="inventory")
	@Retry(name="inventory")
	public CompletableFuture<String> creatOrder(@RequestBody OrderRequest order) {
		
		return CompletableFuture.supplyAsync(()->orderService.placeOrder(order));
	}
	public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest,RuntimeException runtimeException) {
		return CompletableFuture.supplyAsync(()->"Ops ! Somthing went wrong please order after sometime");
	}
	
}
