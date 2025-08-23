package com.dangminhphuc.dev.transaction.xml.foobar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class BarServiceImpl implements BarService {
    private static final Logger logger = LoggerFactory.getLogger(BarServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public BarServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertBar(String name) {
        logger.info("BAR_SERVICE: Starting new transaction (REQUIRES_NEW) for Bar.");
        jdbcTemplate.update("INSERT INTO bar (name) VALUES (?)", name);
        logger.info("BAR_SERVICE: Committing new transaction for Bar.");
    }
}
