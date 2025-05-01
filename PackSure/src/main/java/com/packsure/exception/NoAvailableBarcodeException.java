package com.packsure.exception;

public class NoAvailableBarcodeException extends RuntimeException {
    public NoAvailableBarcodeException(String message) {
        super(message);
    }
}
