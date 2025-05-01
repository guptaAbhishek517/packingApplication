package com.packsure.controller;


import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.IOException;
@RestController
@RequestMapping("/api")
public class BarcodeImageController {
  
	private final String imageDirectory = "/home/shivit/Desktop/PACKSURE/PackSure/uploads/barcodes/barcodes/";
	
	@GetMapping("/barcodes/{fileName}.png")
	public ResponseEntity<FileSystemResource> getBarcodeImage(@PathVariable("fileName") String fileName) throws IOException {
	    File file = new File(imageDirectory + fileName + ".png");
	    if (file.exists()) {
	        FileSystemResource resource = new FileSystemResource(file);
	        return ResponseEntity.ok()
	        		.contentType(MediaType.IMAGE_PNG)
	                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
	                .body(resource);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}

