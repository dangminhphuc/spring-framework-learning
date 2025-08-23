package com.dangminhphuc.dev.transaction.xml.foobar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class FooServiceImpl implements FooService {
    private static final Logger logger = LoggerFactory.getLogger(FooServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final BarService barService;

    public FooServiceImpl(DataSource dataSource, BarService barService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.barService = barService;
    }

    @Override
    public void insertFooRequired(String name) {
        logger.info("FOO_SERVICE: Inserting Foo '{}' with PROPAGATION_REQUIRED.", name);
        jdbcTemplate.update("INSERT INTO foo (name) VALUES (?)", name);
    }

    @Override
    public void insertFooRequiredThenRollback(String name) {
        logger.info("FOO_SERVICE: Inserting Foo '{}' with PROPAGATION_REQUIRED, then rolling back.", name);
        jdbcTemplate.update("INSERT INTO foo (name) VALUES (?)", name);
        throw new RuntimeException("Intentional rollback");
    }

    @Override
    public void outerRequired_innerRequiresNew_thenRollbackOuter(String fooName, String barName) {
        logger.info("FOO_SERVICE: Starting outer transaction (REQUIRED).");
        jdbcTemplate.update("INSERT INTO foo (name) VALUES (?)", fooName);

        try {
            // This will run in a new, independent transaction
            barService.insertBar(barName);
        } catch (Exception e) {
            logger.error("FOO_SERVICE: Unexpected exception from BarService", e);
        }

        logger.info("FOO_SERVICE: Throwing exception to roll back outer transaction.");
        throw new RuntimeException("Intentional rollback for outer transaction");
    }
}
