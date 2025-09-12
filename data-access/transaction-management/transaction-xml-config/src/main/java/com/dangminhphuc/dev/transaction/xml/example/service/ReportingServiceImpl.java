package com.dangminhphuc.dev.transaction.xml.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ReportingServiceImpl implements ReportingService {

    private static final Logger logger = LoggerFactory.getLogger(ReportingServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public ReportingServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int getOrderCountWithReadUncommitted() {
        logger.info("Reading order count with READ_UNCOMMITTED isolation.");
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders", Integer.class);
    }

    @Override
    public int getOrderCountWithReadCommitted() {
        logger.info("Reading order count with READ_COMMITTED isolation.");
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders", Integer.class);
    }

    @Override
    public void processLongRunningReport() {
        logger.info("Starting long-running report. This should time out.");
        try {
            Thread.sleep(3000); // Sleep for 3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("Long-running report finished processing (but should have timed out).");
    }
}
