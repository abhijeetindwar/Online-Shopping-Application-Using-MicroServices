package com.orderservice1.orderService1.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.orderservice1.orderService1.dto.InventoryResponse;
import com.orderservice1.orderService1.dto.OrderLineItemsDto;
import com.orderservice1.orderService1.dto.OrderRequest;
import com.orderservice1.orderService1.events.OrderPlacedEvent;
import com.orderservice1.orderService1.model.Order;
import com.orderservice1.orderService1.model.OrderLineItems;
import com.orderservice1.orderService1.repository.OrderRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	private final OrderRepo orderRepo;
	private final WebClient.Builder webClientBuilder;
	private final Tracer tracer;
	private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
	public String placeOrder(OrderRequest orderRequest) {
		Order order=new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItems> orderItems=orderRequest.getOrderItemsDto().stream().map(mapper->mapToOrder(mapper)).collect(Collectors.toList());
		order.setOrderItems(orderItems);
		List<String> skuCodes=order.getOrderItems().stream().map(OrderLineItems::getSkuCode).collect(Collectors.toList());
		//call inventory service
		Span inventeroyServiceLoolUp=tracer.nextSpan().name("inventoryServiceLookUp");
		try(Tracer.SpanInScope spanInScope=tracer.withSpan(inventeroyServiceLoolUp.start())){
			InventoryResponse[] inventoryResponses=webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
					UriBuilder->UriBuilder.queryParam("skuCode",skuCodes).build())
					.retrieve().bodyToMono(InventoryResponse[].class).block();
			Boolean result=Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isStock);
			if(result) {
				orderRepo.save(order);
				kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
				return  "Order placed successfully";
			}else {
				throw new IllegalArgumentException("Product is out of stock, please order after some time");
			}
		}finally {
			inventeroyServiceLoolUp.end();
			
		}
		
	
		
	}

	private OrderLineItems mapToOrder(OrderLineItemsDto mapper) {
		// TODO Auto-generated method stub
		OrderLineItems orderItems=new OrderLineItems();
		orderItems.setPrice(mapper.getPrice());
		orderItems.setQuantity(mapper.getQuantity());
		orderItems.setSkuCode(mapper.getSkuCode());
		return orderItems;
	}

}
