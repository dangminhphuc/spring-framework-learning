package com.dangminhphuc.dev.transaction.xml;

import com.dangminhphuc.dev.transaction.xml.example.exception.InsufficientStockException;
import com.dangminhphuc.dev.transaction.xml.example.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:ecommerce-context.xml")
public class EcommerceXmlConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(EcommerceXmlConfigTest.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        // Clean up tables before each test to ensure isolation
        jdbcTemplate.execute("TRUNCATE TABLE orders");
        jdbcTemplate.execute("TRUNCATE TABLE audit_log");
        jdbcTemplate.update("UPDATE products SET stock = 10 WHERE name = 'Laptop'");
    }

    @Test
    @DisplayName("Should place order successfully")
    void testPlaceOrder_Success() throws Exception {
        logger.info("--- Running testPlaceOrder_Success ---");
        orderService.placeOrder("dangminhphuc", false, false);

        // Assertions
        Integer orderCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders WHERE customer_name = 'dangminhphuc' AND status = 'COMPLETED'", Integer.class);
        assertEquals(1, orderCount);

        Integer stockCount = jdbcTemplate.queryForObject("SELECT stock FROM products WHERE name = 'Laptop'", Integer.class);
        assertEquals(9, stockCount);

        Integer auditCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM audit_log", Integer.class);
        assertEquals(1, auditCount);
        logger.info("--- Finished testPlaceOrder_Success ---");
    }

    @Test
    @DisplayName("Should rollback order on stock failure but keep audit log")
    void testPlaceOrder_RollbackOnStockFailure() {
        logger.info("--- Running testPlaceOrder_RollbackOnStockFailure ---");
        // This call is expected to fail with an InsufficientStockException
        assertThrows(InsufficientStockException.class, () -> {
            orderService.placeOrder("Gemini", true, false);
        });

        // Assertions
        // 1. The order should have been rolled back.
        int orderCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders WHERE customer_name = 'Gemini'", Integer.class);
        assertEquals(0, orderCount);

        // 2. The stock level should NOT have changed.
        int stockCount = jdbcTemplate.queryForObject("SELECT stock FROM products WHERE name = 'Laptop'", Integer.class);
        assertEquals(10, stockCount);

        // 3. The audit log (REQUIRES_NEW) should have been committed independently.
        int auditCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM audit_log WHERE event_name LIKE '%Gemini%'", Integer.class);
        assertEquals(1, auditCount);
        logger.info("--- Finished testPlaceOrder_RollbackOnStockFailure ---");
    }
}
