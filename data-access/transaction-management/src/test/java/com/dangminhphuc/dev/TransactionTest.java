package com.dangminhphuc.dev;

import com.dangminhphuc.dev.transaction.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TransactionTest {
    private final Transaction fooService;

    private final Transaction barService;

    private final GammaService gammaService;

    @Autowired
    public TransactionTest(@Qualifier("fooService") Transaction fooService,
                           @Qualifier("barService") Transaction barService,
                           GammaService gammaService) {
        this.fooService = fooService;
        this.barService = barService;
        this.gammaService = gammaService;
    }

    @Test
    @DisplayName("Test successful transaction commit")
    void testTransactionCommit() {
        // Pre-assertion
        assertEquals(0, fooService.count());
        assertEquals(0, barService.count());

        // Execute the successful transaction
//        fooService.insertFooAndBarSuccessfully(new Foo("Test Foo Commit"), new Bar("Test Bar Commit"));

        // Assert that the data was committed
        assertEquals(1, fooService.count(), "Foo should have been committed.");
        assertEquals(1, barService.count(), "Bar should have been committed.");
    }

    @Test
    @DisplayName("Test transaction rollback on exception")
    void testTransactionRollback() {
        // Pre-assertion
        assertEquals(0, fooService.count());

        // Execute the transaction that will throw an exception
        assertThrows(RuntimeException.class, () -> fooService.REQUIRED_FAILURE(
        ));

        // After the transaction, we expect no Foo or Bar to be inserted
        assertEquals(0, fooService.count());
    }

    @Nested
    @DisplayName("Advanced Transactional Behaviors")
    class AdvancedTransactionalBehaviorTests {

        @Test
        @DisplayName("Test rollback for checked exception")
        void testRollbackForCheckedException() {
            // Pre-assertion
            assertEquals(0, fooService.count());

            // Execute the transaction that will throw a checked exception
//            assertThrows(java.io.IOException.class, () -> fooService.insertFooAndBarWithCheckedException(
//                    new Foo("Test Foo Checked Exception"),
//                    new Bar("Test Bar Checked Exception")
//            ));

            // After the transaction, we expect no Foo to be inserted
            assertEquals(0, fooService.count(), "Transaction should have rolled back due to checked exception.");
        }

        @Test
        @DisplayName("Test REQUIRES_NEW propagation")
        public void testPropagationRequiresNew() {
            // Pre-assertion
            assertEquals(0, barService.count());
            assertEquals(0, barService.count());

            // Execute the transaction with REQUIRES_NEW propagation
//            assertThrows(RuntimeException.class, () -> fooService.insertFooAndBarWithPropagation(
//                    new Foo("Test Foo REQUIRES_NEW"),
//                    new Bar("Test Bar REQUIRES_NEW")
//            ));

            // Assert that the Bar was inserted due to REQUIRES_NEW propagation
            assertEquals(1, barService.count(), "Bar should have been inserted due to REQUIRES_NEW propagation.");
            assertEquals(0, fooService.count(), "Foo should not have been inserted due to rollback.");
        }

        @Test
        @DisplayName("Test REQUIRED propagation with rollback")
        void required_fooSucceedsBarFail_rollbackAll() {
            // Pre-assertion
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());

            // Execute the transaction with REQUIRED propagation
            assertThrows(RuntimeException.class, () -> gammaService.REQUIRED_execOnly1TxSucceed());

            // Assert that no Foo or Bar was inserted due to rollback
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());
        }

        @Test
        @DisplayName("Test REQUIRED propagation with rollback")
        void required_fooAndBarSucceeds_commitAll() {
            // Pre-assertion
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());

            // Execute the transaction with REQUIRED propagation
            gammaService.REQUIRED_execBothTxSucceed();

            // Assert that no Foo or Bar was inserted due to rollback
            assertEquals(1, fooService.count());
            assertEquals(1, barService.count());
        }

        @Test
        void REQUIRED_NEW_fooSucceedsBarFail_rollbackFoo() {
            // Pre-assertion
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());

            // Execute the transaction with REQUIRED_NEW propagation
            gammaService.REQUIRED_NEW_execBothTxSucceed();

            // Assert that no Foo was inserted due to rollback
            assertEquals(1, fooService.count());
            assertEquals(1, barService.count());
            assertEquals(2, gammaService.getSteps().size());
        }


        @Test
        void REQUIRED_NEW_fooSucceedsBarFail_rollbackFoo2() {
            // Pre-assertion
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());

            // Execute the transaction with REQUIRED_NEW propagation
            assertThrows(RuntimeException.class, () -> gammaService.REQUIRED_NEW_execOnly1TxSucceed());

            // Assert that no Foo was inserted due to rollback
            assertEquals(1, fooService.count());
            assertEquals(0, barService.count());
            assertEquals(1, gammaService.getSteps().size());
        }
    }
}