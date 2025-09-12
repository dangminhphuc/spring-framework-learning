package com.dangminhphuc.dev.transaction.xml.foobar;

import com.dangminhphuc.dev.transaction.xml.foobar.service.CallerService;
import com.dangminhphuc.dev.transaction.xml.foobar.service.DataReaderService;
import com.dangminhphuc.dev.transaction.xml.util.ConcurrentTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:foobar-context.xml")
public class TransactionSettingBasedXmlTest {

    private static final Logger logger = LoggerFactory.getLogger(TransactionSettingBasedXmlTest.class);

    @Autowired
    private CallerService callerService;
    @Autowired
    private DataReaderService dataReaderService;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;
    private ConcurrentTestHelper concurrentTestHelper;

    @BeforeEach
    void setup() {
        this.concurrentTestHelper = new ConcurrentTestHelper(transactionManager);

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcTemplate.execute("TRUNCATE TABLE transaction_entity");
    }

    @Nested
    @DisplayName("Transaction Propagation Tests")
    class PropagationTest {

        @Test
        @DisplayName("Caller REQUIRED is OK, Callee REQUIRED is ERROR -> Both Rollback")
        void testCallerRequiredOK_calleeRequiredError_thenRollbackBoth() {
            assertThrows(RuntimeException.class, () -> callerService.callerRequiredOK_calleeRequiredError_thenRollbackBoth());

            Integer callerCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLER'", Integer.class);
            Integer calleeCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLEE'", Integer.class);

            assertEquals(0, callerCount, "Caller transaction should have been rolled back.");
            assertEquals(0, calleeCount, "Callee transaction should have been rolled back.");
        }

        @Test
        @DisplayName("Caller REQUIRED is ERROR, Callee REQUIRED is OK -> Both Rollback")
        void callerRequiredError_calleeRequiredOK_thenRollbackBoth() {
            assertThrows(RuntimeException.class, () -> callerService.callerRequiredError_calleeRequiredOK_thenRollbackBoth());
            Integer callerCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLER'", Integer.class);
            Integer calleeCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLEE'", Integer.class);

            assertEquals(0, callerCount, "Caller transaction should have been rolled back.");
            assertEquals(0, calleeCount, "Callee transaction should have been rolled back.");
        }

        @Test
        @DisplayName("Caller REQUIRED is ERROR, Callee REQUIRED is ERROR -> Both Rollback")
        void callerRequiredError_calleeRequiredError_thenRollbackBoth() {
            assertThrows(RuntimeException.class, () -> callerService.callerRequiredError_calleeRequiredError_thenRollbackBoth());
            Integer callerCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLER'", Integer.class);
            Integer calleeCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLEE'", Integer.class);

            assertEquals(0, callerCount, "Caller transaction should have been rolled back.");
            assertEquals(0, calleeCount, "Callee transaction should have been rolled back.");
        }

        @Test
        @DisplayName("Caller REQUIRED is ERROR, Callee REQUIRED_NEW is OK -> Commit Callee and Rollback Caller")
        void callerRequiredError_calleeRequiredNewOK_thenCommitCalleeAndRollbackCaller() {
            assertThrows(RuntimeException.class, () -> callerService.callerRequiredError_calleeRequiredNewOK_thenCommitCalleeAndRollbackCaller());
            Integer callerCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLER'", Integer.class);
            Integer calleeCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLEE'", Integer.class);

            assertEquals(0, callerCount, "Caller transaction should have been rolled back.");
            assertEquals(1, calleeCount, "Callee transaction should have been committed.");
        }

        @Test
        @DisplayName("Caller REQUIRED_NEW is OK, Callee REQUIRED is ERROR -> Both Rollback")
        void callerRequiredNewOK_calleeRequiredError_thenRollbackBoth() {
            assertThrows(RuntimeException.class, () -> callerService.callerRequiredNewOK_calleeRequiredError_thenRollbackBoth());
            Integer callerCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLER'", Integer.class);
            Integer calleeCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLEE'", Integer.class);

            assertEquals(0, callerCount, "Caller transaction should have been rolled back.");
            assertEquals(0, calleeCount, "Callee transaction should have been rolled back.");
        }

        @Test
        @DisplayName("Caller REQUIRED_NEW is ERROR, Callee REQUIRED is OK -> Both Rollback")
        void callerRequiredNewError_calleeRequiredOK_thenRollbackBoth() {
            assertThrows(RuntimeException.class, () -> callerService.callerRequiredNewError_calleeRequiredOK_thenRollbackBoth());
            Integer callerCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLER'", Integer.class);
            Integer calleeCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = 'CALLEE'", Integer.class);

            assertEquals(0, callerCount, "Caller transaction should have been rolled back.");
            assertEquals(0, calleeCount, "Callee transaction should have been rolled back.");
        }
    }

    @Nested
    @DisplayName("Transaction Isolation Level Tests")
    class IsolationTest {
        @Test
        @DisplayName("ISOLATION_READ_UNCOMMITTED: Should perform a dirty read")
        void testIsolation_ReadUncommitted() {
            // Define the writer's action
            Runnable writerAction = () -> jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "concurrentOuter");

            // Define the reader's action
            Supplier<Integer> readerAction = () -> dataReaderService.countOutersWithReadUncommitted();

            // Run the concurrent test and get the reader's result
            int count = concurrentTestHelper.runConcurrentWriterAndReader(writerAction, readerAction);

            // Assert the result
            assertEquals(1, count, "Should have read the uncommitted data (dirty read).");
        }

        @Test
        @DisplayName("ISOLATION_READ_COMMITTED: Should prevent a dirty read")
        void testIsolation_ReadCommitted() {
            // Define the writer's action
            Runnable writerAction = () -> jdbcTemplate.update("INSERT INTO transaction_entity (name) VALUES (?)", "concurrentOuter");

            // Define the reader's action
            Supplier<Integer> readerAction = () -> dataReaderService.countOutersWithReadCommitted();

            // Run the concurrent test and get the reader's result
            int count = concurrentTestHelper.runConcurrentWriterAndReader(writerAction, readerAction);

            // Assert the result
            assertEquals(0, count, "Should not have read the uncommitted data.");
        }

//        @Test
//        @DisplayName("ISOLATION_REPEATABLE_READ: Should prevent a non-repeatable read")
//        void testIsolation_RepeatableRead() {
//            // Setup: Insert an initial record
//            final String initialName = "initialName";
//            final String updatedName = "updatedName";
//            callerService.callerRequiredOK_calleeRequiredError_thenRollbackBoth();
//
//            // Define the concurrent action that will update the name
//            Runnable concurrentUpdateAction = () -> callerService.update(initialName, updatedName);
//
//            // Run the test method which reads, invokes the action, then reads again
//            List<String> results = dataReaderService.readNameTwiceInTransaction(initialName, concurrentUpdateAction);
//
//            String firstRead = results.get(0);
//            String secondRead = results.get(1);
//
//            // Assertions
//            assertEquals(initialName, firstRead, "First read should see the initial name.");
//            assertEquals(initialName, secondRead, "Second read should still see the initial name, despite the concurrent update.");
//
//            // Verify that the update DID happen and commit after our transaction was over
//            int finalCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction_entity WHERE name = ?", Integer.class, updatedName);
//            assertEquals(1, finalCount, "The concurrent update should have been committed.");
//        }
    }
}