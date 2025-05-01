
package com.packsure.service;

import com.packsure.dto.BarcodeDTO;
import com.packsure.dto.BarcodeRequestDTO;
import com.packsure.dto.BarcodeResponseDTO;
import com.packsure.dto.ItemDTO;
import com.packsure.entity.Barcode;
import com.packsure.entity.Item;
import com.packsure.entity.Order;
import com.packsure.exception.BarcodeAlreadyDispatchedException;
import com.packsure.exception.BarcodeNotFoundException;
import com.packsure.repository.BarcodeRepository;
import com.packsure.repository.OrderRepository;
import com.packsure.service.BarcodePoolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarcodeService {

    @Autowired
    private BarcodeRepository barcodeRepository;

    @Autowired
    private BarcodePoolService barcodePoolService;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderService orderService;

//    public BarcodeResponseDTO createBarcodeWithItems(BarcodeRequestDTO request) {
//        
//        Order order = orderRepository.findById(request.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Order ID not found"));
//
//        if ("PACKED".equalsIgnoreCase(order.getStatus())) {
//            throw new RuntimeException("This order is already packed.");
//        }
//
//        
//        Barcode barcode = new Barcode();
//        barcode.setBarcodeNumber(request.getBarcodeNumber());
//        barcode.setPackedAt(LocalDateTime.now());
//        barcode.setStatus("PACKED");
//        barcode.setOrder(order); 
//
//        
//        List<Item> items = request.getItems().stream()
//                .map(itemDTO -> {
//                    Item item = new Item();
//                    item.setName(itemDTO.getName());
//                    item.setQuantity(itemDTO.getQuantity());
//                    item.setRate(itemDTO.getRate());
//                    item.setAmount(itemDTO.getAmount());
//                    item.setDescription(itemDTO.getDescription());
//                    item.setBarcode(barcode);
//                    return item;
//                }).collect(Collectors.toList());
//
//        barcode.setItems(items);
//
//        
//        double totalAmount = items.stream().mapToDouble(Item::getAmount).sum();
//        barcode.setTotalAmount(totalAmount);
//
//       
//        barcodeRepository.save(barcode);
//
//        
//        order.setStatus("PACKED");
//        orderRepository.save(order);
//
//        
//        return new BarcodeResponseDTO(
//                barcode.getBarcodeNumber(),
//                request.getItems(),
//                barcode.getStatus()
//        );
//    }
    
    
    public BarcodeResponseDTO createBarcodeWithItems(BarcodeRequestDTO request) {

        // 1. Fetch the existing barcode
        Barcode barcode = barcodeRepository.findByBarcodeNumber(request.getBarcodeNumber())
            .orElseThrow(() -> new RuntimeException("Barcode not found"));

        // 2. Check if already packed
        if ("PACKED".equalsIgnoreCase(barcode.getStatus())) {
            throw new RuntimeException("This barcode is already packed.");
        }

        // 3. Get the associated order
        Order order = barcode.getOrder();
        if (order == null) {
            throw new RuntimeException("No order associated with this barcode.");
        }

        // 4. Create item list
        List<Item> items = request.getItems().stream()
            .map(itemDTO -> {
                Item item = new Item();
                item.setName(itemDTO.getName());
                item.setQuantity(itemDTO.getQuantity());
                item.setRate(itemDTO.getRate());
                item.setAmount(itemDTO.getAmount());
                item.setDescription(itemDTO.getDescription());
                item.setBarcode(barcode);  // link to existing barcode
                return item;
            }).collect(Collectors.toList());

        // 5. Link items and update barcode
        barcode.setItems(items);
        barcode.setPackedAt(LocalDateTime.now());
        barcode.setStatus("PACKED");

        double totalAmount = items.stream().mapToDouble(Item::getAmount).sum();
        barcode.setTotalAmount(totalAmount);

        // 6. Save everything
        barcodeRepository.save(barcode);

        // Optional: update order status to PACKED
        order.setStatus("PACKED");
        orderRepository.save(order);

        // 7. Return response
        return new BarcodeResponseDTO(
            barcode.getBarcodeNumber(),
            request.getItems(),
            barcode.getStatus()
        );
    }



    
//    public BarcodeResponseDTO dispatchBarcode(String barcodeNumber) {
//      
//        Barcode barcode = barcodeRepository.findByBarcodeNumber(barcodeNumber)
//                .orElseThrow(() -> new BarcodeNotFoundException("Barcode not found!"));
//        
//        
//        if ("DISPATCHED".equalsIgnoreCase(barcode.getStatus())) {
//            throw new BarcodeAlreadyDispatchedException("This barcode is already dispatched.");
//        }
//
//        
//        barcode.setStatus("DISPATCHED");
//        barcode.setDispatchedAt(LocalDateTime.now());
//        barcodeRepository.save(barcode); 
//
//        
//        List<ItemDTO> itemDTOs = barcode.getItems().stream()
//                .map(item -> new ItemDTO(item.getName(),item.getQuantity(), item.getRate(), item.getAmount(),item.getDescription())) // Assuming ItemDTO has name, code, and price
//                .collect(Collectors.toList());
//
//       
//        return new BarcodeResponseDTO(barcodeNumber, itemDTOs, barcode.getStatus());
//    }

    
    public List<String> getPackedBarcodes() {
        return barcodeRepository.findAll().stream()
                .filter(b -> "PACKED".equals(b.getStatus()))
                .map(Barcode::getBarcodeNumber)
                .collect(Collectors.toList());
    }
    
   
    public List<String> getAllDispatchedBarcodes() {
        return barcodeRepository.findAll().stream()
                .filter(b -> "DISPATCHED".equals(b.getStatus()))
                .map(Barcode::getBarcodeNumber)
                .collect(Collectors.toList());
    }

    public Page<BarcodeDTO> getAllBarcodes(Pageable pageable) {
        Page<Barcode> barcodesPage = barcodeRepository.findAll(pageable);

        Page<BarcodeDTO> barcodeDTOPage = barcodesPage.map(barcode -> {
            BarcodeDTO dto = new BarcodeDTO();
            dto.setId(barcode.getId());
            dto.setBarcodeNumber(barcode.getBarcodeNumber());
            
            String barcodeImagePath = barcode.getBarcodeImagePath();
            System.out.println("Barcode Path: " + barcodeImagePath);  
            
            dto.setBarcodeImagePath(barcodeImagePath);
            dto.setTotalAmount(barcode.getTotalAmount());  
            dto.setPackedAt(barcode.getPackedAt());
            dto.setDispatchedAt(barcode.getDispatchedAt());
            dto.setStatus(barcode.getStatus());

            return dto;
        });
		return barcodeDTOPage;
    }
    
    // new updated code
    
    public BarcodeResponseDTO getBarcodeOrderDetails(String barcodeNumber) {
		Barcode barcode = barcodeRepository.findByBarcodeNumber(barcodeNumber)
				.orElseThrow(() -> new BarcodeNotFoundException("Barcode not found!"));

		Order order = barcode.getOrder();
		if (order == null) {
			throw new RuntimeException("No order associated with this barcode");
		}

		List<ItemDTO> itemDTOs = barcode.getItems().stream()
				.map(item -> new ItemDTO(
						item.getName(),
						item.getQuantity(),
						item.getRate(),
						item.getAmount(),
						item.getDescription()))
				.collect(Collectors.toList());

		return new BarcodeResponseDTO(
				barcode.getBarcodeNumber(),
				itemDTOs,
				barcode.getStatus()
			
		);
	}

	public String packBarcode(String barcodeNumber) {
		Barcode barcode = barcodeRepository.findByBarcodeNumber(barcodeNumber)
				.orElseThrow(() -> new BarcodeNotFoundException("Barcode not found!"));

		if ("PACKED".equalsIgnoreCase(barcode.getStatus()) || "DISPATCHED".equalsIgnoreCase(barcode.getStatus())) {
			throw new RuntimeException("This item is already packed or dispatched.");
		}

		barcode.setStatus("PACKED");
		barcode.setPackedAt(LocalDateTime.now());
		barcodeRepository.save(barcode);

		return "Item packed successfully.";
	}

	public BarcodeResponseDTO dispatchBarcode(String barcodeNumber) {
		Barcode barcode = barcodeRepository.findByBarcodeNumber(barcodeNumber)
				.orElseThrow(() -> new BarcodeNotFoundException("Barcode not found!"));

		if (!"PACKED".equalsIgnoreCase(barcode.getStatus())) {
			throw new RuntimeException("Cannot dispatch. Item is not packed.");
		}

		if ("DISPATCHED".equalsIgnoreCase(barcode.getStatus())) {
			throw new BarcodeAlreadyDispatchedException("This barcode is already dispatched.");
		}

		barcode.setStatus("DISPATCHED");
		barcode.setDispatchedAt(LocalDateTime.now());
		barcodeRepository.save(barcode);
		
		  List<ItemDTO> itemDTOs = barcode.getItems().stream()
                .map(item -> new ItemDTO(item.getName(),item.getQuantity(), item.getRate(), item.getAmount(),item.getDescription())) // Assuming ItemDTO has name, code, and price
                .collect(Collectors.toList());
		  return new BarcodeResponseDTO(barcodeNumber, itemDTOs, barcode.getStatus());
	}
}

