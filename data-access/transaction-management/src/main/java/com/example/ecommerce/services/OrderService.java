package com.example.ecommerce.services;

import com.example.ecommerce.exceptions.PaymentGatewayException;

public interface OrderService {
    void placeOrder(String customerName, boolean simulateStockFailure, boolean simulatePaymentFailure) throws PaymentGatewayException;
}
