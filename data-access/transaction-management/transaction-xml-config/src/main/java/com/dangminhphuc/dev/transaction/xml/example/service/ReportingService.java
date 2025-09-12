package com.dangminhphuc.dev.transaction.xml.example.service;

public interface ReportingService {

    int getOrderCountWithReadUncommitted();

    int getOrderCountWithReadCommitted();

    void processLongRunningReport();
}
