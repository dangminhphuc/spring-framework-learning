package com.dangminhphuc.dev.transactionboundevents;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class TransactionBoundEventsTest {

    @Autowired
    private CallerService callerService;

    @MockitoBean
    private CalleeEventsListener listener;

    @Test
    public void testTransactionCommit() {
        this.callerService.transactionOK("dangminhphuc");

        verify(listener).BEFORE_COMMIT(any(CalleeEvent.class));
        verify(listener).AFTER_COMMIT(any(CalleeEvent.class));
        verify(listener).AFTER_COMPLETION(any(CalleeEvent.class));

        verify(listener, never()).AFTER_ROLLBACK(any(CalleeEvent.class));
    }

    @Test
    public void testTransactionRollback() {
        assertThrows(RuntimeException.class, () -> {
            this.callerService.transactionError("dangminhphuc");
        });

        verify(listener).AFTER_ROLLBACK(any(CalleeEvent.class));
        verify(listener).AFTER_COMPLETION(any(CalleeEvent.class));

        verify(listener, never()).BEFORE_COMMIT(any(CalleeEvent.class));
        verify(listener, never()).AFTER_COMMIT(any(CalleeEvent.class));
    }
}
