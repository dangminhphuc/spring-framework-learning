package com.dangminhphuc.dev.transaction.xml.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class AuditServiceImpl implements AuditService {

    private static final Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public AuditServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void logEvent(String eventName) {
        logger.info("Logging event: '{}'. Propagation: REQUIRES_NEW.", eventName);
        jdbcTemplate.update("INSERT INTO audit_log (event_name, event_timestamp) VALUES (?, NOW())", eventName);
        logger.info("Event log committed.");
    }
}
