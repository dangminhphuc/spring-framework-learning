package com.dangminhphuc.dev.transaction.xml.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * A utility to simplify testing concurrent database transactions.
 */
public class ConcurrentTestHelper {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrentTestHelper.class);

    private final TransactionTemplate transactionTemplate;

    public ConcurrentTestHelper(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    /**
     * Executes a writer action within a transaction that is guaranteed to roll back,
     * while concurrently executing a reader action to check the intermediate state.
     *
     * @param writerAction The action to perform inside the writer's transaction (e.g., an insert).
     * @param readerAction The action to perform while the writer's transaction is open.
     * @param <T>          The return type of the reader action.
     * @return The result from the reader action.
     */
    public <T> T runConcurrentWriterAndReader(Runnable writerAction, Supplier<T> readerAction) {
        CountDownLatch writerReadyLatch = new CountDownLatch(1);
        CountDownLatch readerDoneLatch = new CountDownLatch(1);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // The writer runs in a separate thread within a programmatic transaction.
        Future<?> writerFuture = executor.submit(() -> {
            transactionTemplate.execute(status -> {
                try {
                    logger.info("HELPER-WRITER: Starting transaction.");
                    writerAction.run();

                    // Signal that the writer has done its work and the reader can proceed.
                    writerReadyLatch.countDown();

                    // Wait for the reader to finish before we roll back.
                    if (!readerDoneLatch.await(5, TimeUnit.SECONDS)) {
                        throw new TimeoutException("Writer timed out waiting for reader to complete.");
                    }
                } catch (Exception e) {
                    logger.error("Error in writer thread", e);
                    throw new RuntimeException(e);
                } finally {
                    // We always roll back to leave the database clean.
                    logger.info("HELPER-WRITER: Rolling back transaction.");
                    status.setRollbackOnly();
                }
                return null;
            });
        });

        try {
            // Wait for the writer to insert its data.
            if (!writerReadyLatch.await(5, TimeUnit.SECONDS)) {
                throw new TimeoutException("Test timed out waiting for writer to be ready.");
            }

            // Now that the writer is waiting, execute the reader action on the main test thread.
            logger.info("HELPER-READER: Executing reader action.");
            return readerAction.get();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // Signal the writer that the reader is done, so it can roll back and finish.
            readerDoneLatch.countDown();
            try {
                // Check for any exceptions that might have occurred in the writer thread.
                writerFuture.get(2, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error("Exception thrown from writer thread during cleanup", e);
            }
            executor.shutdownNow();
        }
    }
}