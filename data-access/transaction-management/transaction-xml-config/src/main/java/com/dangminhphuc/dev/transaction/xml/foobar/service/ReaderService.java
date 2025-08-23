package com.dangminhphuc.dev.transaction.xml.foobar.service;

import java.util.List;

public interface ReaderService {
    int countFoosWithReadUncommitted();

    int countFoosWithReadCommitted();

    List<String> readNameTwiceInTransaction(String queriedName, Runnable writerAction);
}
