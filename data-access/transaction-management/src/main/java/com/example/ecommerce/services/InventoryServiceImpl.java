package com.example.ecommerce.services;

import com.example.ecommerce.exceptions.InsufficientStockException;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class InventoryServiceImpl implements InventoryService {

    private final JdbcTemplate jdbcTemplate;

    public InventoryServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void reserveStock(String item, int quantity) {
        System.out.println("INVENTORY_SERVICE: Reserving stock for '" + item + "'. Propagation: NESTED.");
        // In a real app, you would check and update stock.
        // Here, we just simulate the update.
        jdbcTemplate.update("UPDATE products SET stock = stock - ? WHERE name = ?", quantity, item);
        System.out.println("INVENTORY_SERVICE: Stock reserved for '" + item + "'.");
    }
}
