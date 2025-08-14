package com.dangminhphuc.dev.transaction;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface Transaction {

    @Transactional(propagation = Propagation.REQUIRED)
    default void PROPAGATION_REQUIRED(boolean ok) {
        insert();
        if (!ok) {
            throw new RuntimeException("Deliberate exception to trigger rollback");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    default void PROPAGATION_REQUIRED_NEW(boolean ok) {
        insert();
        if (!ok) {
            throw new RuntimeException("Deliberate exception to trigger rollback");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    default void PROPAGATION_NESTED(boolean ok) {
        insert();
        if (!ok) {
            throw new RuntimeException("Deliberate exception to trigger rollback");
        }
    }

    void insert();

    Integer count();

}
