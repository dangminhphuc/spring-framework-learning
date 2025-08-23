package com.example.ecommerce;

import com.example.ecommerce.exceptions.InsufficientStockException;
import com.example.ecommerce.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/application-context.xml")
public class TransactionScenarioTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        // Clean up tables before each test to ensure isolation
        jdbcTemplate.execute("TRUNCATE TABLE orders");
        jdbcTemplate.execute("TRUNCATE TABLE audit_log");
        jdbcTemplate.update("UPDATE products SET stock = 10 WHERE name = 'Laptop'");
    }

    @Test
    public void testPlaceOrder_Success() throws Exception {
        System.out.println("\n--- Running testPlaceOrder_Success ---");
        orderService.placeOrder("Phuc", false, false);

        // Assertions
        int orderCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders WHERE customer_name = 'Phuc' AND status = 'COMPLETED'", Integer.class);
        assertEquals(1, orderCount);

        int stockCount = jdbcTemplate.queryForObject("SELECT stock FROM products WHERE name = 'Laptop'", Integer.class);
        assertEquals(9, stockCount);

        int auditCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM audit_log", Integer.class);
        assertEquals(1, auditCount);
        System.out.println("--- Finished testPlaceOrder_Success ---");
    }

    @Test
    public void testPlaceOrder_RollbackOnStockFailure() {
        System.out.println("\n--- Running testPlaceOrder_RollbackOnStockFailure ---");
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
        System.out.println("--- Finished testPlaceOrder_RollbackOnStockFailure ---");
    }
}
