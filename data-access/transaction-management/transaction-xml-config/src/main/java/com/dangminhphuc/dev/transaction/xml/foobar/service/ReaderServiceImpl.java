package com.dangminhphuc.dev.transaction.xml.foobar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class ReaderServiceImpl implements ReaderService {
    private static final Logger logger = LoggerFactory.getLogger(ReaderServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public ReaderServiceImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int countFoosWithReadUncommitted() {
        logger.info("READER_SERVICE: Counting Foos with READ_UNCOMMITTED.");
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo", Integer.class);
    }

    @Override
    public int countFoosWithReadCommitted() {
        logger.info("READER_SERVICE: Counting Foos with READ_COMMITTED.");
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo", Integer.class);
    }
}
