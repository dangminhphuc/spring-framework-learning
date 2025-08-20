package com.dangminhphuc.dev.transaction;

import com.dangminhphuc.dev.transaction.config.AppConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class IsolationTest {

    private static final ExecutorService pool = Executors.newFixedThreadPool(2);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager txManager;

    @AfterAll
    static void tearDown() {
        pool.shutdown();
    }

    @BeforeEach
    void setup() {
        // Ensure a clean state before each test
        jdbcTemplate.execute("TRUNCATE TABLE account");
        jdbcTemplate.update("INSERT INTO account (id, balance) VALUES (?, ?)", 1, 100);
    }

    @Test
    @DisplayName("READ_UNCOMMITTED: A reader should see uncommitted changes from a writer transaction.")
    void givenUncommittedWrite_whenReaderUsesReadUncommitted_thenDirtyReadOccurs() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        // TX1 (Writer): Starts a transaction, updates a value, signals the reader, then waits before rolling back.
        Future<Void> writerFuture = pool.submit(() -> runInTx(Isolation.READ_COMMITTED, () -> {
            jdbcTemplate.update("UPDATE account SET balance = ? WHERE id = ?", 999, 1);
            latch.countDown(); // Signal reader that the update has happened
            Thread.sleep(1000); // Keep the transaction open long enough for the reader to read
            throw new RuntimeException("Intentional rollback to clean up"); // Force rollback
        }));

        // TX2 (Reader): Waits for the signal, then reads the data using READ_UNCOMMITTED.
        Future<Integer> readerFuture = pool.submit(() -> runInTx(Isolation.READ_UNCOMMITTED, () -> {
            latch.await(); // Wait for the writer to make its change
            return jdbcTemplate.queryForObject("SELECT balance FROM account WHERE id = ?", Integer.class, 1);
        }));

        // Then: The reader should see the uncommitted value '999'.
        Integer readValue = readerFuture.get();
        assertEquals(999, readValue, "A dirty read should see the uncommitted balance of 999");

        // Cleanup: Ensure the writer thread completes (and rolls back).
        assertThrows(ExecutionException.class, writerFuture::get, "Writer transaction should roll back as intended.");
    }

    @Test
    @DisplayName("READ_COMMITTED: A reader should NOT see uncommitted changes.")
    void givenUncommittedWrite_whenReaderUsesReadCommitted_thenDirtyReadIsPrevented() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        // TX1 (Writer): Same as before, updates a value and holds the transaction open.
        Future<Void> writerFuture = pool.submit(() -> runInTx(Isolation.READ_COMMITTED, () -> {
            jdbcTemplate.update("UPDATE account SET balance = ? WHERE id = ?", 999, 1);
            latch.countDown(); // Signal reader
            Thread.sleep(1000); // Keep TX open
            throw new RuntimeException("Intentional rollback");
        }));

        // TX2 (Reader): Waits, then reads data using READ_COMMITTED.
        Future<Integer> readerFuture = pool.submit(() -> runInTx(Isolation.READ_COMMITTED, () -> {
            latch.await();
            return jdbcTemplate.queryForObject("SELECT balance FROM account WHERE id = ?", Integer.class, 1);
        }));

        // Then: The reader should see the original, committed value '100'.
        Integer readValue = readerFuture.get();
        assertEquals(100, readValue, "READ_COMMITTED should see the original balance of 100");

        // Cleanup
        assertThrows(ExecutionException.class, writerFuture::get);
    }

    @Test
    @DisplayName("READ_COMMITTED: A reader can perform a non-repeatable read.")
    void givenReadCommittedIsolation_whenAnotherTxCommitsUpdate_thenNonRepeatableReadOccurs() throws Exception {
        CountDownLatch writerSignal = new CountDownLatch(1);
        CountDownLatch readerSignal = new CountDownLatch(1);

        // When: A reader reads data twice, with a committed update from another transaction in between.
        Future<List<Integer>> readerFuture = pool.submit(() -> runInTx(Isolation.READ_COMMITTED, () -> {
            Integer firstRead = jdbcTemplate.queryForObject("SELECT balance FROM account WHERE id = ?", Integer.class, 1);
            writerSignal.countDown(); // Signal writer to proceed
            readerSignal.await();     // Wait for writer to commit
            Integer secondRead = jdbcTemplate.queryForObject("SELECT balance FROM account WHERE id = ?", Integer.class, 1);
            return List.of(firstRead, secondRead);
        }));

        // And: A writer waits for the first read, then updates the data and commits.
        Future<Void> writerFuture = pool.submit(() -> {
            writerSignal.await(); // Wait for reader's first read
            runInTx(Isolation.READ_COMMITTED, () -> {
                jdbcTemplate.update("UPDATE account SET balance = ? WHERE id = ?", 500, 1);
                return null; // This commits the transaction
            });
            readerSignal.countDown(); // Signal reader to proceed
            return null;
        });

        // Then: The two reads should see different values.
        List<Integer> reads = readerFuture.get();
        assertEquals(100, reads.get(0), "First read should see the original value.");
        assertEquals(500, reads.get(1), "Second read should see the new, committed value.");

        writerFuture.get(); // Ensure writer completes
    }

    @Test
    @DisplayName("REPEATABLE_READ: A reader is protected from non-repeatable reads.")
    void givenRepeatableReadIsolation_whenAnotherTxCommitsUpdate_thenNonRepeatableReadIsPrevented() throws Exception {
        CountDownLatch writerSignal = new CountDownLatch(1);
        CountDownLatch readerSignal = new CountDownLatch(1);

        // When: A reader reads data twice.
        Future<List<Integer>> readerFuture = pool.submit(() -> runInTx(Isolation.REPEATABLE_READ, () -> {
            Integer firstRead = jdbcTemplate.queryForObject("SELECT balance FROM account WHERE id = ?", Integer.class, 1);
            writerSignal.countDown();
            readerSignal.await();
            Integer secondRead = jdbcTemplate.queryForObject("SELECT balance FROM account WHERE id = ?", Integer.class, 1);
            return List.of(firstRead, secondRead);
        }));

        // And: A writer commits an update in between the reads.
        Future<Void> writerFuture = pool.submit(() -> {
            writerSignal.await();
            runInTx(Isolation.READ_COMMITTED, () -> {
                jdbcTemplate.update("UPDATE account SET balance = ? WHERE id = ?", 500, 1);
                return null;
            });
            readerSignal.countDown();
            return null;
        });

        // Then: Both reads should see the same original value.
        List<Integer> reads = readerFuture.get();
        assertEquals(100, reads.get(0), "First read should see the original value.");
        assertEquals(100, reads.get(1), "Second read should also see the original value.");

        writerFuture.get();
    }

    @Test
    @DisplayName("REPEATABLE_READ: This isolation level in H2 prevents phantom reads.")
    void givenRepeatableReadIsolation_whenAnotherTxCommitsInsert_thenPhantomReadIsPreventedInH2() throws Exception {
        CountDownLatch writerSignal = new CountDownLatch(1);
        CountDownLatch readerSignal = new CountDownLatch(1);

        // When: A reader counts rows twice.
        Future<List<Integer>> readerFuture = pool.submit(() -> runInTx(Isolation.REPEATABLE_READ, () -> {
            Integer firstRead = jdbcTemplate.queryForObject("SELECT count(*) FROM account WHERE balance > 50", Integer.class);
            writerSignal.countDown(); // Signal writer to proceed
            readerSignal.await();     // Wait for writer to commit
            Integer secondRead = jdbcTemplate.queryForObject("SELECT count(*) FROM account WHERE balance > 50", Integer.class);
            return List.of(firstRead, secondRead);
        }));

        // And: A writer commits an insert in between the reads.
        Future<Void> writerFuture = pool.submit(() -> {
            writerSignal.await();
            runInTx(Isolation.READ_COMMITTED, () -> {
                jdbcTemplate.update("INSERT INTO account (id, balance) VALUES (?, ?)", 2, 200);
                return null; // This commits the transaction
            });
            readerSignal.countDown(); // Signal reader to proceed
            return null;
        });

        // Then: The two reads should see the same count because H2's REPEATABLE_READ is stricter than the SQL standard.
        List<Integer> reads = readerFuture.get();
        assertEquals(1, reads.get(0), "First count should be 1.");
        assertEquals(1, reads.get(1), "Second count should also be 1, as H2 prevents phantom reads at this level.");

        writerFuture.get(); // Ensure writer completes
    }

    @Test
    @DisplayName("SERIALIZABLE: A reader is protected from phantom reads.")
    void givenSerializableIsolation_whenAnotherTxCommitsInsert_thenPhantomReadIsPrevented() throws Exception {
        CountDownLatch writerSignal = new CountDownLatch(1);
        CountDownLatch readerSignal = new CountDownLatch(1);

        // When: A reader counts rows twice.
        Future<List<Integer>> readerFuture = pool.submit(() -> runInTx(Isolation.SERIALIZABLE, () -> {
            Integer firstRead = jdbcTemplate.queryForObject("SELECT count(*) FROM account WHERE balance > 50", Integer.class);
            writerSignal.countDown();
            readerSignal.await();
            Integer secondRead = jdbcTemplate.queryForObject("SELECT count(*) FROM account WHERE balance > 50", Integer.class);
            return List.of(firstRead, secondRead);
        }));

        // And: A writer attempts to commit an insert in between the reads.
        Future<Void> writerFuture = pool.submit(() -> {
            writerSignal.await();
            runInTx(Isolation.READ_COMMITTED, () -> {
                // In a real SERIALIZABLE scenario, this update would likely block or fail.
                // The exact behavior depends on the database (e.g., H2, PostgreSQL).
                jdbcTemplate.update("INSERT INTO account (id, balance) VALUES (?, ?)", 2, 200);
                return null;
            });
            readerSignal.countDown();
            return null;
        });

        // Then: Both counts should be the same.
        List<Integer> reads = readerFuture.get();
        assertEquals(1, reads.get(0), "First count should be 1.");
        assertEquals(1, reads.get(1), "Second count should also be 1, as the phantom is prevented.");

        writerFuture.get();
    }

    private <T> T runInTx(Isolation isolation, Callable<T> action) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(isolation.value());
        TransactionStatus status = txManager.getTransaction(def);
        try {
            T result = action.call();
            txManager.commit(status);
            return result;
        } catch (Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
    }
}