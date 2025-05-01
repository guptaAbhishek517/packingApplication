package com.packsure.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.packsure.dto.OrderItemDTO;
import com.packsure.entity.BarcodePool;
import com.packsure.entity.Order;
import com.packsure.entity.OrderItem;
import com.packsure.exception.BarcodeAlreadyPackedException;
import com.packsure.exception.ResourceNotFoundException;
import com.packsure.repository.BarcodePoolRepository;
//import com.packsure.repository.BarcodeRepository;
import com.packsure.repository.OrderRepository;

import jakarta.transaction.Transactional;

import com.packsure.dto.OrderDTO;
//import com.packsure.entity.Barcode;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private BarcodePoolRepository barcodepoolRepo;
	
	
//	@Autowired
//	private BarcodeRepository barcodeRepository;

	public OrderDTO getOrderItemsByOrderId(Long orderId) {
		
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		OrderDTO dto = new OrderDTO();
		dto.setOrderId(order.getId());
		dto.setCustomerName(order.getCustomerName());
		dto.setCustomerEmail(order.getCustomerEmail());
		dto.setCustomerPhone(order.getCustomerPhone());
		dto.setCustomerAddress(order.getCustomerAddress());
		dto.setOrderDate(order.getOrderDate());
		dto.setStatus(order.getStatus());

		List<OrderItemDTO> items = order.getItems().stream().map(item -> {
			OrderItemDTO itemDTO = new OrderItemDTO();
			itemDTO.setItemId(item.getId());
			itemDTO.setItemName(item.getItemName());
			itemDTO.setQuantity(item.getQuantity());
			itemDTO.setPricePerUnit(item.getPricePerUnit());
			itemDTO.setTotalPrice(item.getTotalPrice());
			return itemDTO;
		}).toList();

		dto.setItems(items);
		return dto;

	}
	
	//create order and assign the avilable barcode 
	
	@Transactional
	public Order createOrderWithItems(OrderDTO orderDto) {
	    Order order = new Order();
	    order.setCustomerName(orderDto.getCustomerName());
	    order.setCustomerAddress(orderDto.getCustomerAddress());
	    order.setCustomerPhone(orderDto.getCustomerPhone());
	    order.setOrderDate(LocalDateTime.now());
	    order.setStatus(orderDto.getStatus());
	   

	    // Step 1: Create OrderItems
	    List<OrderItem> orderItems = orderDto.getItems().stream().map(itemDto -> {
	        OrderItem item = new OrderItem();
	        item.setItemName(itemDto.getItemName());
	        item.setQuantity(itemDto.getQuantity());
	        item.setPricePerUnit(itemDto.getPricePerUnit());
	        item.setTotalPrice(itemDto.getQuantity() * itemDto.getPricePerUnit());
	        item.setOrder(order);
	        return item;
	    }).collect(Collectors.toList());
	    order.setItems(orderItems);

	    // Step 2: Get an unused BarcodePool entry
	    BarcodePool pool = barcodepoolRepo.findFirstByIsUsedFalse()
	        .orElseThrow(() -> new RuntimeException("No available barcodes in pool"));

	    // Step 3: Create new Barcode from the pool
	    order.setBarcodeNumber(pool.getBarcodeNumber());
	    
//	    barcode.setBarcodeNumber(pool.getBarcodeNumber());
//	    barcode.setBarcodeImagePath(pool.getBarcodeImagePath());
//	    barcode.setPackedBy("system");
//	    barcode.setBranch("xyz");
//	    barcode.setCompanyId(1L);
//	    barcode.setPackedAt(LocalDateTime.now());
//	    barcode.setStatus("NOT_PACKED");
//	    barcode.setOrder(order); // associate the barcode with the order
//
//	    // Step 4: Save the Barcode
//	    barcodeRepository.save(barcode);
//
//	    // Step 5: Assign Barcode and BarcodePool to Order
//	    order.setBarcode(barcode);
	    
	    
	    order.setBarcodepool(pool);

	    // Step 6: Save Order (cascades OrderItems)
	    Order savedOrder = orderRepository.save(order);

	    // Step 7: Mark BarcodePool as used
	    pool.setUsed(true);
	    barcodepoolRepo.save(pool);

	    return savedOrder;
	}

	public Page<OrderDTO> getAllOrders(Pageable pageable) {
	    Page<Order> ordersPage = orderRepository.findAll(pageable); 

	    return ordersPage.map(order -> {
	        OrderDTO orderDTO = new OrderDTO();
	        orderDTO.setOrderId(order.getId());
	        orderDTO.setCustomerName(order.getCustomerName());
	        orderDTO.setCustomerAddress(order.getCustomerAddress());
	        orderDTO.setCustomerPhone(order.getCustomerPhone());
	        orderDTO.setStatus(order.getStatus());
	        orderDTO.setPackedAt(order.getPackedAt());
	        orderDTO.setDispatchedAt(order.getDispatchedAt());
	        
	        
	     // Directly fetch barcodeNumber from BarcodePool (One-to-One relationship)
	        if (order.getBarcodepool() != null) {
	            orderDTO.setBarcodeNumber(order.getBarcodepool().getBarcodeNumber());
	        } else {
	            orderDTO.setBarcodeNumber("--"); // fallback if BarcodePool is not associated
	        }
	        return orderDTO;
	    });
	}
	
	
	
	 public Order getOrderByBarcode(String barcode) {
//	        Barcode barcodeEntity = barcodeRepository.findByBarcodeNumber(barcode)
//	            .orElseThrow(() -> new RuntimeException("Barcode not found"));
	        
	       
	        Order order = orderRepository.findByBarcodeNumber(barcode);
	        		
	        
	        if ("PACKED".equalsIgnoreCase(order.getStatus())) {
	            throw new BarcodeAlreadyPackedException("Order is already packed for this barcode.");
	        }

	        return order;  // Fetch the order linked to the barcode
	    }

	 public void markOrderAsPacked(String barcode) {
//		     Barcode barcodeEntity = barcodeRepository.findByBarcodeNumber(barcode)
//		            .orElseThrow(() -> new RuntimeException("Barcode not found"));
		        
		 
		        
				 
	     	 Order order = orderRepository.findByBarcodeNumber(barcode);

	       
	        if ("PACKED".equalsIgnoreCase(order.getStatus())) {
	            throw new IllegalStateException("Order is already packed");
	        }

	        order.setStatus("PACKED");
	        order.setPackedAt(LocalDateTime.now());
	        orderRepository.save(order);
	    }
	 
	 

	public void markOrderAsDispatched(String barcode) {
//		 Barcode barcodeEntity = barcodeRepository.findByBarcodeNumber(barcode)
//				 .orElseThrow(()-> new RuntimeException("Barcode not found"));
		 
		 Order order = orderRepository.findByBarcodeNumber(barcode);
		 
		 if (order == null) {
		        throw new RuntimeException("Order not found for the barcode: " + barcode);
		    }
		 
		 String currentStatus = order.getStatus();
		 
		 if("DISPATCHED".equalsIgnoreCase(order.getStatus())) {
			 throw new IllegalStateException("Order is already dispatched");
		 }
		 
		    if ("PACKED".equalsIgnoreCase(currentStatus)) {
		        order.setStatus("DISPATCHED");
		        order.setDispatchedAt(LocalDateTime.now());
		        orderRepository.save(order);
		    } else {
		        throw new IllegalStateException("Order status must be 'PACKED' to dispatch. Current: " + currentStatus);
		    }
		
	}
}
