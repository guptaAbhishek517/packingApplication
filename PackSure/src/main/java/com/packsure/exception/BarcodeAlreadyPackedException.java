package com.packsure.exception;

public class BarcodeAlreadyPackedException extends RuntimeException {
    public BarcodeAlreadyPackedException(String message) {
        super(message);
    }
}