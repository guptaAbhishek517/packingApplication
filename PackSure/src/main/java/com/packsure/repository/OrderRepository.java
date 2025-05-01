package com.packsure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.packsure.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Order findByBarcodeNumber(String barcode);

	

}