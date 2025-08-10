package com.dangminhphuc.dev.transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("barService")
public class BarServiceImpl implements Transactionaly {

    private final JdbcTemplate jdbcTemplate;

    public BarServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void insertBar(Bar bar) {
////        System.out.println("--- Inside BarServiceImpl.insertBar (REQUIRES_NEW) ---");
////        System.out.println("Inserting Bar: " + bar.getName());
//        jdbcTemplate.update("INSERT INTO BAR (NAME) VALUES (?)", bar.getName());

    @Override
    public JdbcTemplate jdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public String tableName() {
        return "BAR";
    }

    /// /        System.out.println("--- Exiting BarServiceImpl.insertBar ---");
//    }
//
//    @Override
//    public void insertWithRequiredPropagation() throws Exception {
//        this.jdbcTemplate.update("INSERT INTO BAR (NAME) VALUES (?)", "INSERT WITH REQUIRED PROPAGATION");
//    }
//
//    @Override
//    public void executeFailure(String name) {
//        this.jdbcTemplate.update("INSERT INTO BAR (NAME) VALUES (?)", name);
//        throw new RuntimeException("Deliberate exception to trigger rollback in BarService");
//    }
//    @Override
//    public void required_succeed() {
//
//    }

//    @Override
//    public void required_failure() {
//
//    }

    @Override
    public void requiredNew_succeed() {

    }

    @Override
    public void requiredNew_failure() {

    }

    @Override
    public void insertFooAndBarSuccessfully(Foo foo, Bar bar) {

    }

    @Override
    public void insertFooAndBarWithCheckedException(Foo foo, Bar bar) throws IOException {

    }

    @Override
    public void insertFooAndBarWithPropagation(Foo foo, Bar bar) {

    }

    @Override
    public Integer count() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BAR", Integer.class);
        return (count == null) ? 0 : count;
    }
}
