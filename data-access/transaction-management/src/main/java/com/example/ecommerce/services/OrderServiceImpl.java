package com.example.ecommerce.services;

import com.example.ecommerce.exceptions.InsufficientStockException;
import com.example.ecommerce.exceptions.PaymentGatewayException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class OrderServiceImpl implements OrderService {

    private final JdbcTemplate jdbcTemplate;
    private final AuditService auditService;
    private final InventoryService inventoryService;

    public OrderServiceImpl(DataSource dataSource, AuditService auditService, InventoryService inventoryService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.auditService = auditService;
        this.inventoryService = inventoryService;
    }

    @Override
    public void placeOrder(String customerName, boolean simulateStockFailure, boolean simulatePaymentFailure) throws PaymentGatewayException {
        System.out.println("ORDER_SERVICE: Order placement started. Propagation: REQUIRED.");

        // 1. Log the attempt (REQUIRES_NEW)
        auditService.logEvent("Order attempt for " + customerName);

        // 2. Create the order record
        System.out.println("ORDER_SERVICE: Creating order record.");
        jdbcTemplate.update("INSERT INTO orders (customer_name, status) VALUES (?, ?)", customerName, "PENDING");

        // 3. Reserve stock for items (NESTED)
        try {
            inventoryService.reserveStock("Laptop", 1);
            if (simulateStockFailure) {
                System.out.println("ORDER_SERVICE: Simulating stock failure...");
                throw new InsufficientStockException("Not enough Laptops in stock!");
            }
        } catch (Exception e) {
            System.out.println("ORDER_SERVICE: Stock reservation failed. Rolling back order.");
            // This exception will propagate up and cause the main transaction to roll back.
            throw e;
        }

        // 4. Process Payment
        System.out.println("ORDER_SERVICE: Processing payment...");
        if (simulatePaymentFailure) {
            System.out.println("ORDER_SERVICE: Simulating payment failure...");
            // This is a checked exception, so it will NOT cause a rollback by default.
            throw new PaymentGatewayException("Payment gateway is down.");
        }

        // 5. If all is well, update order status
        jdbcTemplate.update("UPDATE orders SET status = ? WHERE customer_name = ?", "COMPLETED", customerName);
        System.out.println("ORDER_SERVICE: Order placement successful.");
    }
}
