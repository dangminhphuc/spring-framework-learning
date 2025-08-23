package com.dangminhphuc.dev.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchProcessingService {

    /**
     * This method simulates a long batch job that will exceed its timeout.
     * The transaction is set to time out after 2 seconds.
     */
    @Transactional(timeout = 2)
    public void runLongBatchJob() {
        try {
            System.out.println("Starting a long batch job that should time out...");
            // Simulate work that takes 3 seconds, which is longer than the timeout.
            Thread.sleep(3000);
            System.out.println("This line should never be reached due to the timeout.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
