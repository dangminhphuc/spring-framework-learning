package com.dangminhphuc.dev.transaction.xml.foobar.service;

import java.util.List;

public interface DataReaderService {
    /**
     * Use Case: MonitorUncommittedChanges_UncommittedRead_thenSeeInProgressData
     * Use Case: QuickDraftCount_DraftCountUncommittedRead_thenSeePreliminaryCounts
     */
    int countOutersWithReadUncommitted();

    /**
     * Use Case: GenerateOfficialReport_CommittedRead_thenSeeOnlyFinalizedData
     * Use Case: DisplayPublicData_PublicViewCommittedRead_thenOnlyShowValidatedInformation
     */
    int countOutersWithReadCommitted();

    /**
     * Use Case: VerifyDataConsistency_RepeatableRead_thenEnsureConsistentViewForComplexCalculations
     * Use Case: ProcessOrderLineItems_RepeatableRead_thenGuaranteeItemDetailsDontChangeDuringProcessing
     */
    List<String> readNameTwiceInTransaction(String queriedName, Runnable writerAction);
}
