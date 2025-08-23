package com.dangminhphuc.dev.transaction.xml;

import com.dangminhphuc.dev.transaction.xml.example.service.ReportingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:ecommerce-context.xml")
public class IsolationLevelTest {

    private static final Logger logger = LoggerFactory.getLogger(IsolationLevelTest.class);

    @Autowired
    private ReportingService reportingService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    // A helper service to run the writer transaction
    @Service
    public static class TransactionalWriter {
        @Autowired
        private DataSource dataSource;

        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public void createOrderAndWait(CountDownLatch writeLatch, CountDownLatch readLatch) throws InterruptedException {
            logger.info("WRITER: Starting transaction and creating order.");
            new JdbcTemplate(dataSource).update("INSERT INTO orders (customer_name, status) VALUES (?, ?)", "Concurrent", "PENDING");
            logger.info("WRITER: Order created. Signaling reader.");
            writeLatch.countDown(); // Signal that writing is done

            logger.info("WRITER: Waiting for reader to finish.");
            readLatch.await(5, TimeUnit.SECONDS); // Wait for the reader

            logger.info("WRITER: Reader finished. Rolling back transaction.");
            // NOTE: By throwing an exception, we ensure this transaction is rolled back.
            throw new RuntimeException("Intentional rollback");
        }
    }

    @BeforeEach
    void setup() {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcTemplate.execute("TRUNCATE TABLE orders");
    }

    @Test
    @DisplayName("Demonstrate READ_UNCOMMITTED vs READ_COMMITTED")
    void testIsolationLevels() throws InterruptedException {
        final CountDownLatch writeLatch = new CountDownLatch(1);
        final CountDownLatch readLatch = new CountDownLatch(1);
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        // Get the helper bean from the context
        TransactionalWriter writer = context.getBean(TransactionalWriter.class);

        // Submit the writer task
        executor.submit(() -> {
            try {
                writer.createOrderAndWait(writeLatch, readLatch);
            } catch (Exception e) {
                logger.info("WRITER: Caught expected exception for rollback: {}", e.getMessage());
            }
        });

        // Wait for the writer to create the order
        writeLatch.await(5, TimeUnit.SECONDS);

        logger.info("MAIN: Writer has created the order. Now reading from another transaction.");

        // Perform reads with different isolation levels
        int countWithReadUncommitted = reportingService.getOrderCountWithReadUncommitted();
        logger.info("READER: READ_UNCOMMITTED saw {} orders.", countWithReadUncommitted);

        int countWithReadCommitted = reportingService.getOrderCountWithReadCommitted();
        logger.info("READER: READ_COMMITTED saw {} orders.", countWithReadCommitted);

        // Signal the writer that reading is done
        readLatch.countDown();

        // Wait for all tasks to finish
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Assertions
        assertEquals(1, countWithReadUncommitted, "READ_UNCOMMITTED should see the uncommitted order (dirty read).");
        assertEquals(0, countWithReadCommitted, "READ_COMMITTED should NOT see the uncommitted order.");

        // Final check to ensure the writer transaction was rolled back
        Integer finalCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders", Integer.class);
        assertEquals(0, finalCount, "The writer transaction should have been rolled back.");

        logger.info("Test finished. Isolation levels behaved as expected.");
    }
}
