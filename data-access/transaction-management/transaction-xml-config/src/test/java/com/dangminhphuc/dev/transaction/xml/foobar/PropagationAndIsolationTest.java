package com.dangminhphuc.dev.transaction.xml.foobar;

import com.dangminhphuc.dev.transaction.xml.foobar.service.FooService;
import com.dangminhphuc.dev.transaction.xml.foobar.service.ReaderService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:foobar-context.xml")
public class PropagationAndIsolationTest {

    private static final Logger logger = LoggerFactory.getLogger(PropagationAndIsolationTest.class);

    @Autowired private FooService fooService;
    @Autowired private ReaderService readerService;
    @Autowired private ApplicationContext context;
    @Autowired private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("TRUNCATE TABLE foo");
        jdbcTemplate.execute("TRUNCATE TABLE bar");
    }

    @Test
    @DisplayName("PROPAGATION_REQUIRED: Should commit the transaction")
    void testPropagation_Required_Commits() {
        fooService.insertFooRequired("committedFoo");
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo WHERE name = 'committedFoo'", Integer.class);
        assertEquals(1, count, "The transaction should have been committed.");
    }

    @Test
    @DisplayName("PROPAGATION_REQUIRED: Should roll back the transaction on error")
    void testPropagation_Required_RollsBack() {
        assertThrows(RuntimeException.class, () -> {
            fooService.insertFooRequiredThenRollback("rolledBackFoo");
        });
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo WHERE name = 'rolledBackFoo'", Integer.class);
        assertEquals(0, count, "The transaction should have been rolled back.");
    }

    @Test
    @DisplayName("PROPAGATION_REQUIRES_NEW: Should commit independently of outer transaction")
    void testPropagation_RequiresNew_CommitsIndependently() {
        assertThrows(RuntimeException.class, () -> {
            fooService.outerRequired_innerRequiresNew_thenRollbackOuter("outerFoo", "innerBar");
        });

        int fooCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo WHERE name = 'outerFoo'", Integer.class);
        assertEquals(0, fooCount, "Outer transaction (Foo) should have been rolled back.");

        int barCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM bar WHERE name = 'innerBar'", Integer.class);
        assertEquals(1, barCount, "Inner transaction (Bar) should have been committed independently.");
    }

    // Helper service for concurrent tests
    @Service
    public static class ConcurrentWriter {
        @Autowired private FooService fooService;
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public void insertFooAndWait(CountDownLatch writeLatch, CountDownLatch readLatch) throws InterruptedException {
            fooService.insertFooRequired("concurrentFoo");
            writeLatch.countDown(); // Signal writer is done
            readLatch.await(5, TimeUnit.SECONDS); // Wait for reader
            throw new RuntimeException("Intentional rollback"); // Rollback
        }
    }


    @Test
    @DisplayName("ISOLATION_READ_UNCOMMITTED: Should perform a dirty read")
    void testIsolation_ReadUncommitted() throws InterruptedException {
        final CountDownLatch writeLatch = new CountDownLatch(1);
        final CountDownLatch readLatch = new CountDownLatch(1);
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        ConcurrentWriter writer = context.getBean(ConcurrentWriter.class);
        executor.submit(() -> {
            try { writer.insertFooAndWait(writeLatch, readLatch); } catch (Exception e) { /* Expected */ }
        });

        writeLatch.await(5, TimeUnit.SECONDS);
        int count = readerService.countFoosWithReadUncommitted();
        readLatch.countDown();
        executor.shutdown();

        assertEquals(1, count, "Should have read the uncommitted data (dirty read).");
    }

    @Test
    @DisplayName("ISOLATION_READ_COMMITTED: Should prevent a dirty read")
    void testIsolation_ReadCommitted() throws InterruptedException {
        final CountDownLatch writeLatch = new CountDownLatch(1);
        final CountDownLatch readLatch = new CountDownLatch(1);
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        ConcurrentWriter writer = context.getBean(ConcurrentWriter.class);
        executor.submit(() -> {
            try { writer.insertFooAndWait(writeLatch, readLatch); } catch (Exception e) { /* Expected */ }
        });

        writeLatch.await(5, TimeUnit.SECONDS);
        int count = readerService.countFoosWithReadCommitted();
        readLatch.countDown();
        executor.shutdown();

        assertEquals(0, count, "Should not have read the uncommitted data.");
    }
}