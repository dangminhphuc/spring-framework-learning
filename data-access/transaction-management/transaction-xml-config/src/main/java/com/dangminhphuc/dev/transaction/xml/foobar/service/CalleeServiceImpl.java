package com.dangminhphuc.dev.transaction.xml.foobar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class CalleeServiceImpl implements CalleeService {
    private static final Logger logger = LoggerFactory.getLogger(CalleeServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public CalleeServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void requiredOK() {
        jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "CALLEE");
    }

    @Override
    public void requiredError() {
        jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "CALLEE");
        throw new RuntimeException("Intentional rollback of Inner transaction");
    }

    @Override
    public void requiredNewOK() {
        jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "CALLEE");
    }

    @Override
    public void requiredNewError() {
        jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "CALLEE");
        throw new RuntimeException("Intentional rollback of Inner transaction");
    }

}