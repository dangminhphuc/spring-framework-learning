package com.dangminhphuc.dev.transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface Transactionaly {

    JdbcTemplate jdbcTemplate();

    String tableName();

    @Transactional(propagation = Propagation.REQUIRED)
    default void required_succeed() {
        jdbcTemplate().update("INSERT INTO " + tableName() + " (NAME) VALUES (?)", "INSERT WITH TRANSACTION");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    default void required_failure() {
        jdbcTemplate().update("INSERT INTO " + tableName() + " (NAME) VALUES (?)", "INSERT WITH REQUIRED PROPAGATION");
        throw new RuntimeException("Deliberate exception to trigger rollback");
    }

    void requiredNew_succeed();

    void requiredNew_failure();

    void insertFooAndBarSuccessfully(Foo foo, Bar bar);

    void insertFooAndBarWithCheckedException(Foo foo, Bar bar) throws java.io.IOException;

    void insertFooAndBarWithPropagation(Foo foo, Bar bar);

    Integer count();

}
