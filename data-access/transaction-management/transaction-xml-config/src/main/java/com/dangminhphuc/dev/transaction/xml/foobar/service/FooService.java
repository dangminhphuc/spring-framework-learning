package com.dangminhphuc.dev.transaction.xml.foobar.service;

public interface FooService {
    void insertFooRequired(String name);
    void insertFooRequiredThenRollback(String name);
    void outerRequired_innerRequiresNew_thenRollbackOuter(String fooName, String barName);
}
