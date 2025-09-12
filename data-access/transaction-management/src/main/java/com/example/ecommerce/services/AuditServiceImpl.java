package com.example.ecommerce.services;

import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class AuditServiceImpl implements AuditService {

    private final JdbcTemplate jdbcTemplate;

    public AuditServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void logEvent(String eventName) {
        System.out.println("AUDIT_SERVICE: Logging event: '" + eventName + "'. Propagation: REQUIRES_NEW.");
        jdbcTemplate.update("INSERT INTO audit_log (event_name, event_timestamp) VALUES (?, NOW())", eventName);
        System.out.println("AUDIT_SERVICE: Event log committed.");
    }
}
