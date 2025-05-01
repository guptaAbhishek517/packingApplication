package com.packsure.exception;

public class BarcodeAlreadyDispatchedException extends RuntimeException {

    public BarcodeAlreadyDispatchedException(String message) {
        super(message);
    }
}
