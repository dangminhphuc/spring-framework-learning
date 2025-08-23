package com.dangminhphuc.dev.transaction.xml.example.service;

import com.dangminhphuc.dev.transaction.xml.example.exception.PaymentGatewayException;

public interface OrderService {
    void placeOrder(String customerName, boolean simulateStockFailure, boolean simulatePaymentFailure) throws PaymentGatewayException;
}
