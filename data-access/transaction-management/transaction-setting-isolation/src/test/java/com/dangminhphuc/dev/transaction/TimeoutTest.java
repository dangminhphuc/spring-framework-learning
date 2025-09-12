package com.dangminhphuc.dev.transaction;

import com.dangminhphuc.dev.transaction.config.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionTimedOutException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class TimeoutTest {

    @Autowired
    private BatchProcessingService batchProcessingService;

    @Test
    @DisplayName("A transaction should time out if it exceeds its configured time limit")
    void givenTransactionWithTimeout_whenExecutionTimeExceedsTimeout_thenTransactionTimesOut() {
        // When: we call a method that takes longer (3 seconds) than its transaction timeout (2 seconds)
        // Then: the transaction manager should throw a TransactionTimedOutException.
        assertThrows(TransactionTimedOutException.class, () -> {
            batchProcessingService.runLongBatchJob();
        }, "Expected a TransactionTimedOutException because the job takes longer than the timeout");
    }
}
