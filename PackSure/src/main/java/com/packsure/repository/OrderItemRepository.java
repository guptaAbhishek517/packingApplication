package com.packsure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.packsure.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
