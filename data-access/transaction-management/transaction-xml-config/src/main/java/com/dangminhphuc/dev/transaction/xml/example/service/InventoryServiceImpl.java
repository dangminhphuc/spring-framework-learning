package com.dangminhphuc.dev.transaction.xml.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public InventoryServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void reserveStock(String item, int quantity) {
        logger.info("Reserving stock for '{}'. Propagation: NESTED.", item);
        // In a real app, you would check and update stock.
        // Here, we just simulate the update.
        jdbcTemplate.update("UPDATE products SET stock = stock - ? WHERE name = ?", quantity, item);
        logger.info("Stock reserved for '{}'.", item);
    }
}
