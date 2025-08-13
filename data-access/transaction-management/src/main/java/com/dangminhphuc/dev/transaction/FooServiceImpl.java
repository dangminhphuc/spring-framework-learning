package com.dangminhphuc.dev.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("fooService")
public class FooServiceImpl implements Transaction {
    private final JdbcTemplate jdbcTemplate;
    private final List<String> instances = new ArrayList<>();

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

}
