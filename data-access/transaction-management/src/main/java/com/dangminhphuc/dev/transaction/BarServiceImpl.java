package com.dangminhphuc.dev.transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("barService")
public class BarServiceImpl implements Transaction {

    private final JdbcTemplate jdbcTemplate;
    private final List<String> instances = new ArrayList<>();

    public BarServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public JdbcTemplate jdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public String tableName() {
        return "BAR";
    }

}
