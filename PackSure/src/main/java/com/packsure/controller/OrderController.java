package com.packsure.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.packsure.dto.OrderDTO;
import com.packsure.entity.Order;
import com.packsure.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/{orderId}/items")
	public ResponseEntity<OrderDTO> getOrderItems(@PathVariable Long orderId) {
		OrderDTO items = orderService.getOrderItemsByOrderId(orderId);
		return ResponseEntity.ok(items);
	}

	@PostMapping("/create")
	public ResponseEntity<Order> createOrderDetails(@RequestBody OrderDTO orderDto) {
		Order order = orderService.createOrderWithItems(orderDto);
		return ResponseEntity.ok(order);

	}

//	@GetMapping("/all")
//	public List<OrderDTO> getAllOrders() {
//		return orderService.getAllOrders(); // Get the orders from the service layer
//	}
	
	
  
	//api endpoint to get orders by barcodeNumber
	  @GetMapping("/by-barcode/{barcode}")
	    public ResponseEntity<Order> getOrderByBarcode(@PathVariable String barcode) {
	        Order order = orderService.getOrderByBarcode(barcode);
	        return ResponseEntity.ok(order);
	    }
	  
	  
	 //api endpoint to make order pack
	  @PutMapping("/{barcode}/pack")
	    public ResponseEntity<?> markOrderAsPacked(@PathVariable String barcode) {
	        try {
	            orderService.markOrderAsPacked(barcode);
	            return ResponseEntity.ok(Map.of("message", "Order Packed"));
	        } catch (IllegalStateException e) {
	            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Something went wrong"));
	        }
	    }
	  
	  //api endpoint to get OrderId
	    @GetMapping("/all")
	    public Page<OrderDTO> getAllOrderID(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        return orderService.getAllOrders(pageable);
	    }
	   
	 //api endpoint to make orderID dispatched
	    @PutMapping("/{barcode}/dispatch")
	    public ResponseEntity<?> markOrderAsDispatched(@PathVariable String barcode) {
	    	  try {
	    	        orderService.markOrderAsDispatched(barcode);
	    	        return ResponseEntity.ok(new HashMap<String, String>() {{
	    	            put("message", "Order dispatched!");
	    	        }});
	    	    } catch (IllegalStateException e) {
	    	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>() {{
	    	            put("error", e.getMessage());
	    	        }});
	    	    } catch (RuntimeException e) {
	    	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
	    	            put("error", "Order not found.");
	    	        }});
	    	    }
	    }
	    
}
