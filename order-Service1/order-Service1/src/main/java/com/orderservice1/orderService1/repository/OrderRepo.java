package com.orderservice1.orderService1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderservice1.orderService1.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
