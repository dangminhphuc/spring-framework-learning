package com.dangminhphuc.dev;

import com.dangminhphuc.dev.transaction.AppConfig;
import com.dangminhphuc.dev.transaction.GammaService;
import com.dangminhphuc.dev.transaction.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PropagationBehaviorTest {

    private final Transaction fooService;

    private final Transaction barService;

    private final GammaService gammaService;

    @Autowired
    public PropagationBehaviorTest(@Qualifier("fooService") Transaction fooService,
                                   @Qualifier("barService") Transaction barService,
                                   GammaService gammaService) {
        this.fooService = fooService;
        this.barService = barService;
        this.gammaService = gammaService;
    }

    @AfterEach
    void tearDown() {
        this.gammaService.clearSteps();
    }

    @Nested
    @DisplayName("Transactional Propagation Behaviors")
    class TransactionalPropagationTests {

        @Test
        @DisplayName("REQUIRED: Inner methods succeed -> all changes are committed")
        void givenRequiredPropagation_whenInnerMethodsSucceed_thenAllChangesAreCommitted() {
            // Pre-assertion
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());

            // Execute the transaction with REQUIRED propagation
            gammaService.REQUIRED_execBothTxSucceed();

            // Assert that both services committed their changes as part of the same transaction.
            assertEquals(1, fooService.count(), "Foo's change should be committed.");
            assertEquals(1, barService.count(), "Bar's change should be committed.");
        }

        @Test
        @DisplayName("REQUIRED: Inner method fails -> entire transaction is rolled back")
        void givenRequiredPropagation_whenInnerMethodFails_thenEntireTransactionIsRolledBack() {
            // Pre-assertion
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());

            // Execute the transaction with REQUIRED propagation
            assertThrows(RuntimeException.class, gammaService::REQUIRED_execOnly1TxSucceed);

            // Assert that the single transaction was rolled back, so no changes were committed.
            assertEquals(0, fooService.count(), "Foo's change should be rolled back.");
            assertEquals(0, barService.count(), "Bar's change should be rolled back.");
        }

        @Test
        @DisplayName("REQUIRES_NEW: Inner methods succeed -> both new transactions are committed")
        void givenRequiresNewPropagation_whenInnerMethodsSucceed_thenBothNewTransactionsAreCommitted() {
            // Pre-assertion
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());
            assertEquals(0, gammaService.getSteps().size());

            // Execute the transaction with REQUIRES_NEW propagation
            gammaService.REQUIRED_NEW_BothTxSucceed();

            // Assert that both services committed their changes in separate, new transactions.
            assertEquals(1, fooService.count(), "Foo's independent transaction should commit.");
            assertEquals(1, barService.count(), "Bar's independent transaction should commit.");
            assertEquals(List.of("START", "FINISH"), gammaService.getSteps(), "Steps should contain START and FINISH due to successful transaction.");
        }

        @Test
        @DisplayName("REQUIRES_NEW: Second inner method fails -> first transaction commits, second rolls back")
        void givenRequiresNewPropagation_whenSecondInnerMethodFails_thenFirstTransactionCommitsAndSecondRollsBack() {
            // Pre-assertion
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());
            assertEquals(0, gammaService.getSteps().size());

            // Execute the transaction with REQUIRES_NEW propagation
            assertThrows(RuntimeException.class, gammaService::REQUIRED_NEW_Only1TxSucceed);

            // Assert that fooService's independent transaction was committed, but barService's was rolled back.
            assertEquals(1, fooService.count(), "Foo's independent transaction should commit successfully.");
            assertEquals(0, barService.count(), "Bar's independent transaction should roll back due to the exception.");
            assertEquals(List.of("START"), gammaService.getSteps(), "Execution should stop after the exception, so FINISH is never added.");
        }

        @Test
        @DisplayName("NESTED: Inner methods succeed -> entire transaction is committed")
        void givenNestedPropagation_whenInnerMethodsSucceed_thenEntireTransactionIsCommitted() {
            // Pre-assertion
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());
            assertEquals(0, gammaService.getSteps().size());

            // Execute the transaction with NESTED propagation
            gammaService.NESTED_bothTxSucceed();

            // Assert that both nested transactions committed along with the outer transaction.
            assertEquals(1, fooService.count(), "Foo's nested transaction should commit with the outer transaction.");
            assertEquals(1, barService.count(), "Bar's nested transaction should commit with the outer transaction.");
            assertEquals(List.of("START", "FINISH"), gammaService.getSteps(), "Steps should contain START and FINISH due to successful transaction.");
        }

        @Test
        @DisplayName("NESTED: Inner method fails -> entire transaction is rolled back")
        void givenNestedPropagation_whenInnerMethodFails_thenEntireTransactionIsRolledBack() {
            // Pre-assertion
            assertEquals(0, fooService.count());
            assertEquals(0, barService.count());
            assertEquals(0, gammaService.getSteps().size());

            // Execute the transaction with NESTED propagation
            assertThrows(RuntimeException.class, gammaService::NESTED_only1TxSucceed);

            // Assert that the exception from the inner nested transaction caused the entire outer transaction to roll back.
            assertEquals(0, fooService.count(), "Foo's work should be rolled back along with the main transaction.");
            assertEquals(0, barService.count(), "Bar's failed work should be rolled back.");
            assertEquals(List.of("START"), gammaService.getSteps(), "Execution should stop after the exception, so FINISH is never added.");
        }
    }
}