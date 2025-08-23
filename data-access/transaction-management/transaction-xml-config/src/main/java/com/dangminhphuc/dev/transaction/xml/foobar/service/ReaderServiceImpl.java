package com.dangminhphuc.dev.transaction.xml.foobar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<String> readNameTwiceInTransaction(String queriedName, Runnable writerAction) {
        List<String> results = new ArrayList<>();

        // First read
        try {
            String firstRead = jdbcTemplate.queryForObject("SELECT name FROM foo WHERE name = ?", String.class, queriedName);
            results.add(firstRead);
            logger.info("READER_SERVICE: First read got name '{}'", firstRead);
        } catch (EmptyResultDataAccessException e) {
            results.add(null);
            logger.info("READER_SERVICE: First read found no record for name '{}'", queriedName);
        }

        // Execute the concurrent action that will modify the data
        logger.info("WRITER_SERVICE: Executing concurrent action.");
        writerAction.run();

        // Second read
        try {
            String secondRead = jdbcTemplate.queryForObject("SELECT name FROM foo WHERE name = ?", String.class, queriedName);
            results.add(secondRead);
            logger.info("READER_SERVICE: Second read got name '{}'", secondRead);
        } catch (EmptyResultDataAccessException e) {
            results.add(null);
            logger.info("READER_SERVICE: Second read found no record for name '{}'", queriedName);
        }

        return results;
    }
}
