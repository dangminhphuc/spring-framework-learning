package com.dangminhphuc.dev.transaction.xml.foobar.service;

public interface ReaderService {
    int countFoosWithReadUncommitted();
    int countFoosWithReadCommitted();
}
