package com.dangminhphuc.dev.transaction.xml.foobar.service;

public interface CallerService {

    // propagation use cases
    void callerRequiredOK_calleeRequiredError_thenRollbackBoth();

    void callerRequiredError_calleeRequiredOK_thenRollbackBoth();

    void callerRequiredError_calleeRequiredError_thenRollbackBoth();

    void callerRequiredError_calleeRequiredNewOK_thenCommitCalleeAndRollbackCaller();

    void callerRequiredNewOK_calleeRequiredError_thenRollbackBoth();

    void callerRequiredNewError_calleeRequiredOK_thenRollbackBoth();

    // isolation level tests
    void update(String oldName, String newName);
}