package com.dangminhphuc.dev.transaction.xml.foobar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class CallerServiceImpl implements CallerService {
    private static final Logger logger = LoggerFactory.getLogger(CallerServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final CalleeService calleeService;

    public CallerServiceImpl(DataSource dataSource, CalleeService calleeService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.calleeService = calleeService;
    }

    @Override
    public void callerRequiredOK_calleeRequiredError_thenRollbackBoth() {
        jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "CALLER");
        calleeService.requiredError();
//        throw new RuntimeException("Intentional rollback of both transactions");
    }

    @Override
    public void callerRequiredError_calleeRequiredOK_thenRollbackBoth() {
        jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "CALLER");
        calleeService.requiredOK();
        throw new RuntimeException("Intentional rollback of both transactions");
    }

    @Override
    public void callerRequiredError_calleeRequiredError_thenRollbackBoth() {
        jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "CALLER");
        calleeService.requiredError();
        throw new RuntimeException("Intentional rollback of both transactions");
    }

    @Override
    public void callerRequiredError_calleeRequiredNewOK_thenCommitCalleeAndRollbackCaller() {
        jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "CALLER");
        calleeService.requiredNewOK();
        throw new RuntimeException("Intentional rollback of outer transaction");
    }

    @Override
    public void callerRequiredNewOK_calleeRequiredError_thenRollbackBoth() {
        jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "CALLER");
        calleeService.requiredError();
    }

    @Override
    public void callerRequiredNewError_calleeRequiredOK_thenRollbackBoth() {
        jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "CALLER");
        calleeService.requiredOK();
        throw new RuntimeException("Intentional rollback of outer transaction");
    }

    @Override
    public void update(String oldName, String newName) {
        logger.info("caller_SERVICE: Updating Outer name from '{}' to '{}'", oldName, newName);
        jdbcTemplate.update("UPDATE transaction_entity SET name = ? WHERE name = ?", newName, oldName);
    }
}