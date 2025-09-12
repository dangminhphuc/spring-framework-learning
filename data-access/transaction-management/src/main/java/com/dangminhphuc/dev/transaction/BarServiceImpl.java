package com.dangminhphuc.dev.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("barService")
public class BarServiceImpl implements Transaction {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BarServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert() {
        this.jdbcTemplate.update("INSERT INTO BAR (NAME) VALUES (?)", "INSERT WITH TRANSACTION");
    }

    @Override
    public Integer count() {
        Integer count = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BAR", Integer.class);
        return (count == null) ? 0 : count;
    }

}
