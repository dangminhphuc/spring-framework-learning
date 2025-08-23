package com.dangminhphuc.dev.transaction.xml.example.service;

import com.dangminhphuc.dev.transaction.xml.example.exception.InsufficientStockException;
import com.dangminhphuc.dev.transaction.xml.example.exception.PaymentGatewayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
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
        logger.info("Order placement started. Propagation: REQUIRED.");

        // 1. Log the attempt (REQUIRES_NEW)
        auditService.logEvent("Order attempt for " + customerName);

        // 2. Create the order record
        logger.info("Creating order record.");
        jdbcTemplate.update("INSERT INTO orders (customer_name, status) VALUES (?, ?)", customerName, "PENDING");

        // 3. Reserve stock for items (NESTED)
        try {
            inventoryService.reserveStock("Laptop", 1);
            if (simulateStockFailure) {
                logger.warn("Simulating stock failure...");
                throw new InsufficientStockException("Not enough Laptops in stock!");
            }
        } catch (Exception e) {
            logger.error("Stock reservation failed. Rolling back order.", e);
            // This exception will propagate up and cause the main transaction to roll back.
            throw e;
        }

        // 4. Process Payment
        logger.info("Processing payment...");
        if (simulatePaymentFailure) {
            logger.warn("Simulating payment failure...");
            // This is a checked exception, so it will NOT cause a rollback by default.
            throw new PaymentGatewayException("Payment gateway is down.");
        }

        // 5. If all is well, update order status
        jdbcTemplate.update("UPDATE orders SET status = ? WHERE customer_name = ?", "COMPLETED", customerName);
        logger.info("Order placement successful.");
    }
}
