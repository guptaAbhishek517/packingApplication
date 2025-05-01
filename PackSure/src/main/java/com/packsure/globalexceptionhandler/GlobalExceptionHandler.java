package com.packsure.globalexceptionhandler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.packsure.exception.BarcodeAlreadyDispatchedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BarcodeAlreadyDispatchedException.class)
    public ResponseEntity<?> handleBarcodeAlreadyDispatchedException(BarcodeAlreadyDispatchedException ex) {
        // Yaha simple JSON banake frontend ko bhejenge
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    // Other exception handlers bhi yaha daal sakte ho
}
