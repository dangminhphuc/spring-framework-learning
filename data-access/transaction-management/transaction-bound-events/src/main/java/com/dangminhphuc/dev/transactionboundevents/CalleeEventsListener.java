package com.dangminhphuc.dev.transactionboundevents;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CalleeEventsListener {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void BEFORE_COMMIT(CalleeEvent event) {
        System.out.println("[BEFORE_COMMIT]     - " + event.getName());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void AFTER_COMMIT(CalleeEvent event) {
        System.out.println("[AFTER_COMMIT]      - " + event.getName());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void AFTER_ROLLBACK(CalleeEvent event) {
        System.out.println("[AFTER_ROLLBACK]    - " + event.getName());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void AFTER_COMPLETION(CalleeEvent event) {
        System.out.println("[AFTER_COMPLETION]  - " + event.getName());
    }

}
