package com.dangminhphuc.dev.transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface Transaction {

    JdbcTemplate jdbcTemplate();

    String tableName();

    @Transactional(propagation = Propagation.REQUIRED)
    default void PROPAGATION_REQUIRED(boolean isOK) {
        jdbcTemplate().update("INSERT INTO " + tableName() + " (NAME) VALUES (?)", tableName());
        if (!isOK) {
            throw new RuntimeException("Deliberate exception to trigger rollback");
        }
    }

    @Deprecated
    @Transactional(propagation = Propagation.REQUIRED)
    default void REQUIRED_FAILURE() {
        jdbcTemplate().update("INSERT INTO " + tableName() + " (NAME) VALUES (?)", "INSERT WITH REQUIRED PROPAGATION");
        throw new RuntimeException("Deliberate exception to trigger rollback");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    default void PROPAGATION_REQUIRED_NEW(boolean isOK) {
        jdbcTemplate().update("INSERT INTO " + tableName() + " (NAME) VALUES (?)", tableName());
        if (!isOK) {
            throw new RuntimeException("Deliberate exception to trigger rollback");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    default void REQUIRED_NEW_FAILED() {
        jdbcTemplate().update("INSERT INTO " + tableName() + " (NAME) VALUES (?)", "INSERT WITH REQUIRED PROPAGATION");
        throw new RuntimeException("Deliberate exception to trigger rollback");
    }

    @Transactional(propagation = Propagation.NESTED)
    default void executeTxSuccessWithNestedPropagation() {
        jdbcTemplate().update("INSERT INTO " + tableName() + " (NAME) VALUES (?)", "INSERT WITH TRANSACTION");
    }

    @Transactional(propagation = Propagation.NESTED)
    default void executeTxFailureWithNestedPropagation() {
        jdbcTemplate().update("INSERT INTO " + tableName() + " (NAME) VALUES (?)", "INSERT WITH REQUIRED PROPAGATION");
        throw new RuntimeException("Deliberate exception to trigger rollback");
    }

    default Integer count() {
        Integer count = jdbcTemplate().queryForObject("SELECT COUNT(*) FROM " + tableName(), Integer.class);
        return (count == null) ? 0 : count;
    }

}
