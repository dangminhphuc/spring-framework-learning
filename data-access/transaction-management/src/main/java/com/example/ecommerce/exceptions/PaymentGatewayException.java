package com.example.ecommerce.exceptions;

public class PaymentGatewayException extends Exception {
    public PaymentGatewayException(String message) {
        super(message);
    }
}
