package com.dangminhphuc.dev.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service("fooService")
public class FooServiceImpl implements Transactionaly {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FooServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public JdbcTemplate jdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public String tableName() {
        return "FOO";
    }

//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public void required_succeed() {
//        jdbcTemplate.update("INSERT INTO FOO (NAME) VALUES (?)", "INSERT WITH NO TRANSACTION");
//    }

//    @Override
//    @Transactional
//    public void required_failure() {
//        jdbcTemplate.update("INSERT INTO FOO (NAME) VALUES (?)", "INSERT WITH REQUIRED PROPAGATION");
//        throw new RuntimeException("Deliberate exception to trigger rollback");
//    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiredNew_succeed() {
        jdbcTemplate.update("INSERT INTO FOO (NAME) VALUES (?)", "INSERT WITH NO TRANSACTION");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiredNew_failure() {
        jdbcTemplate.update("INSERT INTO FOO (NAME) VALUES (?)", "INSERT WITH REQUIRED PROPAGATION");
        throw new RuntimeException("Deliberate exception to trigger rollback");
    }


    @Override
    @Transactional
    public void insertFooAndBarSuccessfully(Foo foo, Bar bar) {
        jdbcTemplate.update("INSERT INTO FOO (NAME) VALUES (?)", foo.getName());
        jdbcTemplate.update("INSERT INTO BAR (NAME) VALUES (?)", bar.getName());
    }

    // This method demonstrates a transaction that rolls back due to a checked exception
    @Override
    @Transactional(rollbackFor = IOException.class)
    public void insertFooAndBarWithCheckedException(Foo foo, Bar bar) throws IOException {
        jdbcTemplate.update("INSERT INTO FOO (NAME) VALUES (?)", foo.getName());
        throw new IOException("Deliberate IOException to trigger rollback");
    }

    @Override
    @Transactional
    public void insertFooAndBarWithPropagation(Foo foo, Bar bar) {
        jdbcTemplate.update("INSERT INTO BAR (NAME) VALUES (?)", "REQUIRES_NEW");
//        barService.insertBar(bar);

        throw new RuntimeException("Deliberate exception to trigger rollback");
    }

    @Override
    public Integer count() {
        Integer i = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO", Integer.class);
        return i == null ? 0 : i;
    }

}
