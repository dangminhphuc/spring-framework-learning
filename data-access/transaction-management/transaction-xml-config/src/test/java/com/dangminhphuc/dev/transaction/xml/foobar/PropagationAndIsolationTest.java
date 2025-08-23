package com.dangminhphuc.dev.transaction.xml.foobar;

import com.dangminhphuc.dev.transaction.xml.foobar.service.FooService;
import com.dangminhphuc.dev.transaction.xml.foobar.service.ReaderService;
import com.dangminhphuc.dev.transaction.xml.util.ConcurrentTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class PropagationAndIsolationTest {

    private static final Logger logger = LoggerFactory.getLogger(PropagationAndIsolationTest.class);

    @Autowired
    private FooService fooService;
    @Autowired
    private ReaderService readerService;
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
        this.jdbcTemplate.execute("TRUNCATE TABLE foo");
        this.jdbcTemplate.execute("TRUNCATE TABLE bar");
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

    @Test
    @DisplayName("ISOLATION_READ_UNCOMMITTED: Should perform a dirty read")
    void testIsolation_ReadUncommitted() {
        // Define the writer's action
        Runnable writerAction = () -> jdbcTemplate.update("INSERT INTO foo (name) VALUES (?)", "concurrentFoo");

        // Define the reader's action
        Supplier<Integer> readerAction = () -> readerService.countFoosWithReadUncommitted();

        // Run the concurrent test and get the reader's result
        int count = concurrentTestHelper.runConcurrentWriterAndReader(writerAction, readerAction);

        // Assert the result
        assertEquals(1, count, "Should have read the uncommitted data (dirty read).");
    }

    @Test
    @DisplayName("ISOLATION_READ_COMMITTED: Should prevent a dirty read")
    void testIsolation_ReadCommitted() {
        // Define the writer's action
        Runnable writerAction = () -> jdbcTemplate.update("INSERT INTO foo (name) VALUES (?)", "concurrentFoo");

        // Define the reader's action
        Supplier<Integer> readerAction = () -> readerService.countFoosWithReadCommitted();

        // Run the concurrent test and get the reader's result
        int count = concurrentTestHelper.runConcurrentWriterAndReader(writerAction, readerAction);

        // Assert the result
        assertEquals(0, count, "Should not have read the uncommitted data.");
    }

    @Test
    @DisplayName("ISOLATION_REPEATABLE_READ: Should prevent a non-repeatable read")
    void testIsolation_RepeatableRead() {
        // Setup: Insert an initial record
        final String initialName = "initialName";
        final String updatedName = "updatedName";
        fooService.insertFooRequired(initialName);

        // Define the concurrent action that will update the name
        Runnable concurrentUpdateAction = () -> fooService.updateFooName(initialName, updatedName);

        // Run the test method which reads, invokes the action, then reads again
        List<String> results = readerService.readNameTwiceInTransaction(initialName, concurrentUpdateAction);

        String firstRead = results.get(0);
        String secondRead = results.get(1);

        // Assertions
        assertEquals(initialName, firstRead, "First read should see the initial name.");
        assertEquals(initialName, secondRead, "Second read should still see the initial name, despite the concurrent update.");

        // Verify that the update DID happen and commit after our transaction was over
        int finalCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo WHERE name = ?", Integer.class, updatedName);
        assertEquals(1, finalCount, "The concurrent update should have been committed.");
    }
}
