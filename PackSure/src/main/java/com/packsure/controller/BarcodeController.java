package com.packsure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.packsure.dto.BarcodeDTO;
import com.packsure.dto.BarcodeRequestDTO;
import com.packsure.dto.BarcodeResponseDTO;
import com.packsure.service.BarcodeService;


@RestController 
@RequestMapping ("/api/barcodes")
public class BarcodeController {

    @Autowired 
    private BarcodeService barcodeService;

    @PostMapping("/pack")
    public ResponseEntity<BarcodeResponseDTO> packItems(@RequestBody BarcodeRequestDTO request) {
        return ResponseEntity.ok(barcodeService.createBarcodeWithItems(request));
    }

//    @PostMapping("/dispatch/{barcodeNumber}")
//    public ResponseEntity<BarcodeResponseDTO> dispatch(@PathVariable String barcodeNumber) {
//    	BarcodeResponseDTO response = barcodeService.dispatchBarcode(barcodeNumber);
//        return ResponseEntity.ok(response);  // Return the response with HTTP 200 OK
//    }
//    
//    @PostMapping("/scan/{barcodeNumber}")
//    public ResponseEntity<BarcodeResponseDTO> scanAndDispatch(@PathVariable String barcodeNumber) {
//    	BarcodeResponseDTO response = barcodeService.dispatchBarcode(barcodeNumber);
//        return ResponseEntity.ok(response);  // Return the response with HTTP 200 OK
//    }

    @GetMapping("/available")
    public ResponseEntity<List<String>> getAvailablePackedBarcodes() {
        return ResponseEntity.ok(barcodeService.getPackedBarcodes());
    }
    
    @GetMapping("/packed")
    public ResponseEntity<List<String>> getPackedBarcodes() {
        List<String> packedBarcodes = barcodeService.getPackedBarcodes();
        return ResponseEntity.ok(packedBarcodes);
    }
    
    @GetMapping("/dispatched")
    public ResponseEntity<List<String>> getAllDispatchedItems() {
        List<String> dispatchedItems = barcodeService.getAllDispatchedBarcodes();
        return ResponseEntity.ok(dispatchedItems);
    }
    
//    @GetMapping("/all")
//    public Page<Barcode>  getAllBarcodes(@RequestParam(defaultValue ="0") int page, @RequestParam(defaultValue = "10" )int size){
//    	Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Order.desc("packedAt")));
//    	Page<Barcode> pageResult = barcodeService.getAllBarcodes(pageable);
//    	 // Map to send data with barcodeImagePath, but keep pagination metadata intact
////        pageResult.getContent().forEach(barcode -> {
////            barcode.setBarcodeImagePath(barcode.getBarcodeImagePath());  // Include image path (no need to re-map to new Barcode object)
////        });
//    	
//    	  // Debug log to check if the image paths are correctly populated
//            pageResult.getContent().forEach(barcode -> {
//            System.out.println("Barcode Path: " + barcode.getBarcodeImagePath());  // Debugging log
//            barcode.setBarcodeImagePath(barcode.getBarcodeImagePath());  // Ensure it's not null
//        });
//    	return pageResult;
//    }
    
    @GetMapping("/all")
    public Page<BarcodeDTO> getAllBarcodes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("packedAt")));

       Page<BarcodeDTO> pageResult = barcodeService.getAllBarcodes(pageable);
       pageResult.getContent().forEach(barcode -> {
       System.out.println("Barcode Path: " + barcode.getBarcodeImagePath());  
       barcode.setBarcodeImagePath(barcode.getBarcodeImagePath());  
       });
        return barcodeService.getAllBarcodes(pageable);
    }
    
    //updated controller
    
	// GET scanned barcode details (scan step)
	@GetMapping("/scan/{barcodeNumber}")
	public ResponseEntity<BarcodeResponseDTO> getOrderDetails(@PathVariable String barcodeNumber) {
		BarcodeResponseDTO response = barcodeService.getBarcodeOrderDetails(barcodeNumber);
		return ResponseEntity.ok(response);
	}

	// POST to pack the item
	@PostMapping("/pack/{barcodeNumber}")
	public ResponseEntity<String> packBarcode(@PathVariable String barcodeNumber) {
		String response = barcodeService.packBarcode(barcodeNumber);
		return ResponseEntity.ok(response);
	}

	// POST to dispatch the item
	@PostMapping("/dispatch/{barcodeNumber}")
	public ResponseEntity<BarcodeResponseDTO> dispatchBarcode(@PathVariable String barcodeNumber) {
		BarcodeResponseDTO response = barcodeService.dispatchBarcode(barcodeNumber);
		return ResponseEntity.ok(response);
	}
}
