package com.packsure.exception;

import org.springframework.web.bind.annotation.ResponseStatus;


public class BarcodeNotFoundException extends RuntimeException {
    public BarcodeNotFoundException(String message) {
        super(message);
    }
}

