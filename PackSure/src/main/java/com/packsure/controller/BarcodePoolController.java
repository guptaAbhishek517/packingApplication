package com.packsure.controller;


import com.packsure.service.BarcodePoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barcode-pool")
public class BarcodePoolController {

    @Autowired
    private BarcodePoolService barcodePoolService;

    
    @GetMapping("/available")
    public List<String> getAvailableBarcodes() {
        return barcodePoolService.getAvailableBarcodes();
    }
    @PostMapping("/mark-as-used/{barcodeNumber}")
    public void markBarcodeAsUsed(@PathVariable String barcodeNumber) {
        barcodePoolService.markBarcodeAsUsed(barcodeNumber);
    }
    
    @PostMapping("/generate")
    public ResponseEntity<String> generateBarcodes(@RequestParam int count) {
        barcodePoolService.generateBarcodeNumbers(count);
        return ResponseEntity.ok(count + " barcode numbers generated successfully!");
    }
    
    @GetMapping("/used")
    public List<String> getUsedBarcodes() {
       
        return barcodePoolService.getUsedBarcodes();
    }
}
