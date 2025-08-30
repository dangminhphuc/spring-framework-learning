package com.dangminhphuc.dev.transactionboundevents;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CallerService {

    private final ApplicationEventPublisher publisher;

    public CallerService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Transactional
    public void transactionOK(String name) {
        this.publisher.publishEvent(new CalleeEvent(this, name));
        System.out.println("SIMULATED COMMIT");
    }

    @Transactional
    public void transactionError(String name) {
        this.publisher.publishEvent(new CalleeEvent(this, name));
        throw new RuntimeException("SIMULATED ERROR");
    }
}
