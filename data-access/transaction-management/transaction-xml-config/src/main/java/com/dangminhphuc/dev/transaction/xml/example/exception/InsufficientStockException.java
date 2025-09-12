package com.dangminhphuc.dev.transaction.xml.example.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
